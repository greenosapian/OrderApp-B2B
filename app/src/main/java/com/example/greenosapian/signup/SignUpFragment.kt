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
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.setLifecycleOwner(this)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        setupFirebaseAuthCallback()
        setupViewModelObservers()
        binding.viewmodel = signUpViewModel
        binding.submitOTPButton.isEnabled = false
//        binding.resendTV.isEnabled = false
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

        signUpViewModel.submitPhoneNumber.observe(viewLifecycleOwner, Observer {
            it?.let {
                submitPhoneNumber(it)
                signUpViewModel.phoneNumberSubmitted()
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
            }
        })
    }

    fun submitPhoneNumber(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(signUpViewModel.getFirebaseAuthCallbacks())          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun submitOTP(otp:String) {
//        Log.i(TAG, "otp received: $otp")
//        try {
//            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, otp)
//            signInWithPhoneAuthCredential(credential)
//        }catch (e: Exception){
//            e.printStackTrace()
//            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun setupFirebaseAuthCallback() {
//        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // This callback will be invoked in two situations:
//                // 1 - Instant verification. In some cases the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without
//                //     user action.
//                Log.d(SignUpViewModel.TAG, "onVerificationCompleted:$credential")
//
//                signInWithPhoneAuthCredential(credential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                // This callback is invoked in an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                Log.w(SignUpViewModel.TAG, "onVerificationFailed", e)
//
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                    // ...
//                } else if (e is FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                    // ...
//                }
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                Log.d(SignUpViewModel.TAG, "onCodeSent:$verificationId")
////                binding.otpSubmitButton?.isEnabled ?: true
//
//                Toast.makeText(context, "OTP Sent", Toast.LENGTH_SHORT).show()
//                // Save verification ID and resending token so we can use them later
//                storedVerificationId = verificationId
//                resendToken = token
//            }
//        }//@callback
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
