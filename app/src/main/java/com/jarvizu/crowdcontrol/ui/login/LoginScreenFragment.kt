package com.jarvizu.crowdcontrol.ui.login

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jarvizu.crowdcontrol.BuildConfig
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.data.model.LoginData
import com.jarvizu.crowdcontrol.databinding.FragmentLoginScreenBinding
import com.jarvizu.crowdcontrol.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginScreenFragment : Fragment() {

    @Inject
    lateinit var authManager : AuthManager

    val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginScreenBinding
    private val uiScope = CoroutineScope(Dispatchers.Main)


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

            textPhoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())
            ccp.registerCarrierNumberEditText(textPhoneNumber)

            if (BuildConfig.DEBUG) {
                textPhoneNumber.setText(R.string.debug_number)
            }

            binding.verificationButton.setOnClickListener() {
                viewModel.storeLoginData(LoginData(Constants.PHONE_NUMBER, ccp.formattedFullNumber))
            }

        }
    }

    fun onClick(view: View?) {
        when(requireView().id) {
            binding.verificationButton.id -> {
                uiScope.launch {

                }
            }
        }
    }
}

