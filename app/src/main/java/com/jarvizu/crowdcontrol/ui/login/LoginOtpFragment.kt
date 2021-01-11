package com.jarvizu.crowdcontrol.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.jarvizu.crowdcontrol.BuildConfig
import com.jarvizu.crowdcontrol.databinding.FragmentOtpScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginOtpFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentOtpScreenBinding
    val args: LoginOtpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpScreenBinding.inflate(inflater, container, false)
        val arguments: LoginOtpFragmentArgs = LoginOtpFragmentArgs.fromBundle(arguments as Bundle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (BuildConfig.DEBUG) {
            binding.otpTextView.setOTP("242424")
        }


        with(binding) {
            otpButton.setOnClickListener {
                val otpCode: String = binding.otpTextView.otp.toString()
            }
        }
    }

}

