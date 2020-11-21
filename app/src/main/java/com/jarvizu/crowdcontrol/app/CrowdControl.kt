package com.jarvizu.crowdcontrol.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
import com.jarvizu.crowdcontrol.R
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class CrowdControl : Application() {

    companion object {

        const val APP_NAME = "crowd_control"

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0) // (Optional) How many method line to show. Default 2
            .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            .tag("Timber") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build();

        val apiKey: String = applicationContext.getString(R.string.api_key)
        Places.initialize(applicationContext, apiKey)
        Stetho.initializeWithDefaults(this);
        Timber.plant(object : MyTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}
