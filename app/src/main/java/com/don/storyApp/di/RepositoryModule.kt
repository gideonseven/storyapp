package com.don.storyApp.di

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.storage.AppPreferences
import com.don.storyApp.domain.repository.auth.AuthRepositoryImpl
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.domain.repository.stories.StoriesRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: StoryApi,
        gson: Gson,
        appPreferences: AppPreferences
    ): IAuthRepository {
        return AuthRepositoryImpl(api, gson, appPreferences)
    }

    @Singleton
    @Provides
    fun provideStoriesRepository(
        api: StoryApi,
        gson: Gson,
        appPreferences: AppPreferences
    ): IStoriesRepository {
        return StoriesRepositoryImpl(api, gson, appPreferences)
    }
}