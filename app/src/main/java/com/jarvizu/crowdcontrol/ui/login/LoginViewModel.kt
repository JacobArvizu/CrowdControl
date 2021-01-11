package com.jarvizu.crowdcontrol.ui.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.data.model.LoginData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginViewModel @ViewModelInject constructor(
    val authManager: AuthManager,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val LOGIN_DATA = "login_data"

    fun storeLoginData(loginData: LoginData) = flow<LoginData> {
        viewModelScope.launch {
            savedStateHandle.set(loginData.key, loginData.value)
            emit(loginData)
        }
    }
}




