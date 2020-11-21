package com.jarvizu.crowdcontrol.ui.login

import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.jarvizu.crowdcontrol.ui.main.MainActivity
import com.orhanobut.hawk.Hawk
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun LoginActivity.startPhoneVerification(
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    phoneNumber: String
) {
    // [START start_phone_auth]
    val options = PhoneAuthOptions.newBuilder(firebaseAuth)
        .setPhoneNumber(phoneNumber)       // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(this)                 // Activity (for callback binding)
        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
    // [END start_phone_auth]
}

fun LoginActivity.phoneAuthCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Timber.d("onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Timber.w("onVerificationFailed%s", e.toString())


            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // [START_EXCLUDE]
                Snackbar.make(
                    findViewById(android.R.id.content), "Quota exceeded.",
                    Snackbar.LENGTH_SHORT
                ).show()
                // [END_EXCLUDE]
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Timber.d("onCodeSent:$verificationId")
            Hawk.put("verification_id", verificationId)
        }
    }

}

fun LoginActivity.verifyPhoneNumberWithCode(code: String) {
    val verificationId = Hawk.get<String>("verification_id")
    // [START verify_with_code]
    val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
    // [END verify_with_code]
    Timber.d("Credential:$credential")
    signInWithPhoneAuthCredential(credential)
}

// [START sign_in_with_phone]
fun LoginActivity.signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Timber.d("signInWithCredential:success")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Sign in failed, display a message and update the UI
                Timber.w("signInWithCredential:failure%s", task.exception.toString())
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    // [START_EXCLUDE silent]
                    // [END_EXCLUDE]
                }

            }
        }
}