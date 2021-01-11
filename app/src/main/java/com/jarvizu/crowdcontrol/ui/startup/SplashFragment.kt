package com.jarvizu.crowdcontrol.ui.startup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.data.AuthState
import com.jarvizu.crowdcontrol.databinding.FragmentSplashBinding
import com.jarvizu.crowdcontrol.ui.login.LoginActivity
import com.jarvizu.crowdcontrol.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding
    lateinit var uiScope: CoroutineScope
    @Inject
    lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        uiScope = CoroutineScope(Dispatchers.Main)
        initialize(uiScope)
        return binding.root
    }

    fun initialize(uiScope: CoroutineScope) {
        uiScope.launch {
            viewModel.getAuthState().collect {
                when(it) {
                    is AuthState.Valid -> findNavController().navigate(R.id.action_splash_to_main)
                    else -> {
                        startActivity(Intent(context, LoginActivity::class.java))
                    }
                }
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}


