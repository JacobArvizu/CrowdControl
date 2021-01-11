package com.jarvizu.crowdcontrol.ui.login


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.*
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.data.model.LoginData
import com.jarvizu.crowdcontrol.databinding.AcitivityLoginBinding
import com.jarvizu.crowdcontrol.ui.main.MainActivity
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    val viewModel: LoginViewModel by viewModels()


    private lateinit var binding: AcitivityLoginBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        callbacks = phoneAuthCallbacks()
    }



    override fun onBackPressed() {
        MaterialDialog(this)
            .title(R.string.confirm_exit)
            .message(R.string.your_message)
            .show {
                positiveButton(R.string.agree) {
                    this@LoginActivity.finish()
                }
                negativeButton(R.string.disagree) {
                    cancel()
                }
            }
        return
    }

    override fun onStart() {
        super.onStart()
    }

    suspend fun startLogin() {
        viewModel.storeLoginData().collect {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d(outState.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d(savedInstanceState.toString())

    }

    private fun initializeLoginFlow() {
        callbacks = phoneAuthCallbacks()
    }

    private fun startPhoneVerification(
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        phoneNumber: String
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    fun verifyPhoneNumberWithCode(code: String) {
        val verificationId = Hawk.get<String>("verification_id")
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        Timber.d("Credential:$credential")
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
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
}
