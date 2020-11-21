package com.jarvizu.crowdcontrol.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jarvizu.crowdcontrol.data.AuthHelper
import com.jarvizu.crowdcontrol.data.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val authManager: AuthHelper,
    @Assisted savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val authState = MutableLiveData<AuthState>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            authState.postValue(AuthState.Loading)
            authManager.getFirebaseUser().map {
                flow { emit(it) }
            }.flowOn(Dispatchers.Default)
                .catch { authState.postValue(AuthState.Invalid) }
                .collect {
                    val firebaseUser = it.single()
                    when {
                        firebaseUser != null -> authState.postValue(AuthState.Valid(firebaseUser))
                        else -> authState.postValue(AuthState.Invalid)
                    }
                }
        }
    }

    fun getAuthState(): LiveData<AuthState> {
        return authState
    }
}

