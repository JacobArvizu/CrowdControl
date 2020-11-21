package com.jarvizu.crowdcontrol.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cafe.adriel.kbus.KBus
import com.jarvizu.crowdcontrol.BuildConfig
import com.jarvizu.crowdcontrol.databinding.FragmentOtpScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginOtpFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentOtpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (BuildConfig.DEBUG) {
            binding.otpTextView.setOTP("242424")
        }

        binding.otpButton.setOnClickListener {
            val otpCode: String = binding.otpTextView.otp.toString()
            KBus.post(LoginActivity.LoginOtpEvent(otpCode))
        }
    }
}

