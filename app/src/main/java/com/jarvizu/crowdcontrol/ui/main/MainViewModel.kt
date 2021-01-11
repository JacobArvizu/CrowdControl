package com.jarvizu.crowdcontrol.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.data.AuthRepository
import com.jarvizu.crowdcontrol.data.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun getAuthState() = authRepository.getAuthData
}