package com.jarvizu.crowdcontrol.data

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthHelper {
    fun getFirebaseUser(): Flow<FirebaseUser?>
}