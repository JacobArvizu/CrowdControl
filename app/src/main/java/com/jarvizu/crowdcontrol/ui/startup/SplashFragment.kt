package com.jarvizu.crowdcontrol.ui.startup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.data.AuthState
import com.jarvizu.crowdcontrol.ui.login.LoginActivity
import com.jarvizu.crowdcontrol.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAuthState().observe(viewLifecycleOwner, Observer {
            when (it) {
                is AuthState.Valid -> {
                    Timber.d("AuthState:Valid")
                    findNavController().navigate(R.id.action_splash_to_main)
                }
                is AuthState.Invalid -> {
                    Timber.d("AuthState:Invalid")
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                is AuthState.Loading -> Timber.d(it.toString())
                else -> {
                    Timber.d("AuthState: Error!")
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}


