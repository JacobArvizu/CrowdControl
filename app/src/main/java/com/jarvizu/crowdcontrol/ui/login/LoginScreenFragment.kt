package com.jarvizu.crowdcontrol.ui.login

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cafe.adriel.kbus.KBus
import com.jarvizu.crowdcontrol.BuildConfig
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.databinding.FragmentLoginScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreenFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginScreenBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {

            if (BuildConfig.DEBUG) {
                textPhoneNumber.setText(R.string.debug_number)
            }

            textPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())
            ccp.registerCarrierNumberEditText(textPhoneNumber)

            verificationButton.setOnClickListener {
                KBus.post(LoginActivity.LoginSubmitEvent(ccp.fullNumberWithPlus))
            }
        }

    }
}
