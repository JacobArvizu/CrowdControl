package com.jarvizu.crowdcontrol.di

import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.data.AuthHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun authManager(authManager: AuthManager): AuthHelper
}