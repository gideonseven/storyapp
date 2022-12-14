package com.don.storyApp

import android.app.Application
import com.don.storyApp.domain.model.AppBuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltAndroidApp
class StoryApplication : Application() {

    @Inject
    lateinit var appBuildConfig: AppBuildConfig

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appBuildConfig.apply {
            prefName = BuildConfig.PREFERENCES_NAME
            appDebug = BuildConfig.DEBUG
        }
    }
}