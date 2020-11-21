package com.jarvizu.crowdcontrol.ui.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel(), LifecycleObserver {


    fun onLoginSubmitClicked(phoneNumber: String) {
        Timber.d("Login click submit:%s", phoneNumber)

    }
}

