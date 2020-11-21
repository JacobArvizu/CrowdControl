package com.jarvizu.crowdcontrol.ui.login


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import cafe.adriel.kbus.KBus
import com.afollestad.materialdialogs.MaterialDialog
import com.droidman.ktoasty.KToasty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.databinding.AcitivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    data class LoginSubmitEvent(val phoneNumber: String)
    data class LoginOtpEvent(val otpCode: String)

    private lateinit var binding: AcitivityLoginBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = AcitivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KBus.subscribe<LoginSubmitEvent>(this) {
            startPhoneVerification(callbacks, it.phoneNumber)
            navigateToOtpScreen()
        }

        KBus.subscribe<LoginOtpEvent>(this) {
            verifyPhoneNumberWithCode(it.otpCode)
        }

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
        navController = Navigation.findNavController(this, R.id.login_nav_host_fragment)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d(outState.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d(savedInstanceState.toString())

    }


    // [END sign_in_with_phone]

    private fun signOut() {
        firebaseAuth.signOut()
    }


    private fun navigateToOtpScreen() {
        KToasty.info(this, "Submitted").show()
        navController.navigate(R.id.action_loginScreenFragment_to_loginOtpFragment)
    }


    private fun enableViews(vararg views: View) {
        for (v in views) {
            v.isEnabled = true
        }
    }

    private fun disableViews(vararg views: View) {
        for (v in views) {
            v.isEnabled = false
        }
    }

}
