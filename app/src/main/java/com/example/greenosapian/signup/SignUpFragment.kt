package com.example.greenosapian.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import com.example.greenosapian.databinding.FragmentSignUpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SignUpFragment : Fragment() {
    private val TAG = "SignUpFragment"
    private lateinit var binding: FragmentSignUpBinding
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.setLifecycleOwner(this)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        setupViewModelObservers()
        binding.viewmodel = signUpViewModel
        binding.submitOTPButton.isEnabled = false
        return binding.root
    }

    private fun setupViewModelObservers() {
        signUpViewModel.phoneNumberError.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.phoneNumberTF.error = it
            }
        })

        signUpViewModel.phoneNumber.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.phoneNumberTF.error = null
            }
        })

        signUpViewModel.otp.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.submitOTPButton.isEnabled = it.length >= 6
            }
        })

        signUpViewModel.credential.observe(viewLifecycleOwner, Observer {
            it?.let {
                signInWithPhoneAuthCredential(it)
                signUpViewModel.signInWithCredentialsComplete()
            }
        })

        //refactor to signle-event live-data
        signUpViewModel.submitPhoneNumber.observe(viewLifecycleOwner, Observer {
            it?.let {
                submitPhoneNumber(
                    signUpViewModel.getPhoneNumber(),
                    signUpViewModel.getFirebaseAuthCallbacks()
                )
                signUpViewModel.phoneNumberSubmitted()
            }
        })
        signUpViewModel.resendOtp.observe(viewLifecycleOwner, Observer {
            it?.let {
                resendVerificationCode(
                    signUpViewModel.getPhoneNumber(),
                    signUpViewModel.getResendToken(),
                    signUpViewModel.getFirebaseAuthCallbacks()
                )
                signUpViewModel.otpResent()
            }
        })
    }

    private fun submitPhoneNumber(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val username = Firebase.auth.currentUser?.displayName
                    if (username.isNullOrEmpty()) {
                        this.findNavController()
                            .navigate(SignUpFragmentDirections.actionSignUpFragmentToProfilePageFragment())
                    } else {
                        this.findNavController()
                            .navigate(SignUpFragmentDirections.actionSignUpFragmentToHomePageFragment())
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
