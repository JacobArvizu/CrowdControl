package com.jarvizu.crowdcontrol.ui.login

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@ExperimentalCoroutinesApi
fun LoginActivity.phoneAuthCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
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


// [START sign_in_with_phone]
