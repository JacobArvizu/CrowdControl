package com.jarvizu.crowdcontrol.di

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

    @JvmStatic
    @Provides
    fun sharedPref(@ApplicationContext application: Context): SharedPreferences =
        application.getSharedPreferences(CrowdControl.APP_NAME, Context.MODE_PRIVATE)


    @Provides
    fun application(application: Context): Context = application

    @Provides
    fun providePlacesClient(@ApplicationContext context: Context) = Places.createClient(context)

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}