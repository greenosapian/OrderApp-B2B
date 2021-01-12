package com.example.greenosapian.signup

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SignUpViewModel : ViewModel() {
    companion object {
        const val TAG = "@SignUpViewModel"
    }

    //firebase auth
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    val otp = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    lateinit var resendOtpTimer: CountDownTimer

    private val _isOtpScreenVisible = MutableLiveData<Boolean>()
    val isOtpScreenVisible: LiveData<Boolean>
        get() = _isOtpScreenVisible

    private val _isResendOtpButtonVisible = MutableLiveData<Boolean>()
    val isResendOtpButtonVisible: LiveData<Boolean>
        get() = _isResendOtpButtonVisible

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String>
        get() = _phoneNumberError

    private val _credential = MutableLiveData<PhoneAuthCredential>()
    val credential: LiveData<PhoneAuthCredential>
        get() = _credential

    private val _resendOtpTimerText = MutableLiveData<String>()
    val resendOtpTimerText: LiveData<String>
        get() = _resendOtpTimerText

    private val _submitPhoneNumber = MutableLiveData<Boolean>()
    val submitPhoneNumber: LiveData<Boolean>
        get() = _submitPhoneNumber

    private val _resendOtp = MutableLiveData<Boolean>()
    val resendOtp: LiveData<Boolean>
        get() = _resendOtp

    private val _isSubmitOtpButtonEnabled = MutableLiveData<Boolean>()
    val isSubmitOtpButtonEnabled: LiveData<Boolean>
        get() = _isSubmitOtpButtonEnabled

    init {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(SignUpViewModel.TAG, "onVerificationCompleted:$credential")

                this@SignUpViewModel._credential.value = credential
//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(SignUpViewModel.TAG, "onVerificationFailed", e)
//                _isSubmitOtpButtonEnabled.value = true
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                resendOtpTimer.start()
                _isSubmitOtpButtonEnabled.value = true
            }
        }//@callback

        resendOtpTimer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                _isResendOtpButtonVisible.value = true
            }

            override fun onTick(p0: Long) {
                _resendOtpTimerText.value = (p0 / 1000L).toString()
            }
        }//@countDownTimer

        _resendOtpTimerText.value = "60"
        _isSubmitOtpButtonEnabled.value = false
    }

    fun getFirebaseAuthCallbacks() = callbacks
    fun getResendToken() = resendToken
    fun getPhoneNumber() = "+91" + phoneNumber.value

    fun onSubmitPhoneNumber() {
        if (phoneNumber.value?.length?.compareTo(10) != 0) {
            _phoneNumberError.value = "Phone number must be 10 digits"
            return;
        }

        _isOtpScreenVisible.value = true
        _submitPhoneNumber.value = true

        Log.i(TAG, "getOtpButtonClicked: ${phoneNumber.value}")
    }

    fun phoneNumberSubmitted() {
        _submitPhoneNumber.value = null
    }

    fun onSubmitOtp() {

        try {
            _credential.value =
                PhoneAuthProvider.getCredential(storedVerificationId!!, otp.value.toString())
            _isSubmitOtpButtonEnabled.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            _credential.value = null
        }
    }

    fun onResendOtp() {
        _resendOtp.value = true
        _isSubmitOtpButtonEnabled.value = false
    }

    fun otpResent() {
        _isResendOtpButtonVisible.value = false
        _resendOtpTimerText.value = "60"
        _resendOtp.value = null
    }

    fun signInWithCredentialsComplete() {
        _credential.value = null
    }
}