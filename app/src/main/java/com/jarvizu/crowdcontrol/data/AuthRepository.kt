package com.jarvizu.crowdcontrol.data

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) : LiveData<AuthState>() {

    val getAuthData = flow<AuthState> {
        val firebaseUser = firebaseAuth.currentUser
        emit(AuthState.Valid(firebaseUser!!))
    }.catch {
        emit(AuthState.Invalid)
    }.flowOn(Dispatchers.IO)
}