package com.jarvizu.crowdcontrol.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import com.jarvizu.crowdcontrol.app.CrowdControl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedPreferences(
            @ApplicationContext application: Context
    ): SharedPreferences {
        return application.getSharedPreferences(
                CrowdControl.APP_NAME,
                Context.MODE_PRIVATE
        )
    }

    @Provides
    fun providePlacesClient(@ApplicationContext context: Context) = Places.createClient(context)

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun application(app: Application): Context = app
}