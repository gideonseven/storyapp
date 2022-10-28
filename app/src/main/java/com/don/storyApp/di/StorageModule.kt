package com.don.storyApp.di

import android.content.Context
import com.don.storyApp.data.storage.AppPreferences
import com.don.storyApp.domain.model.AppBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        appBuildConfig: AppBuildConfig
    ): AppPreferences = AppPreferences(
        context.getSharedPreferences(
            appBuildConfig.prefName,
            Context.MODE_PRIVATE
        )
    )
}