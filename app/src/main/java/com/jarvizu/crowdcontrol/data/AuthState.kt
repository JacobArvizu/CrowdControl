package com.jarvizu.crowdcontrol.data

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    data class Valid(val firebaseUser: FirebaseUser?) : AuthState()
    object Invalid : AuthState()
    object Loading : AuthState()
}