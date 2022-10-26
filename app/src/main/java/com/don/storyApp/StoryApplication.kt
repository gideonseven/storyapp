package com.don.storyApp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltAndroidApp
class StoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}