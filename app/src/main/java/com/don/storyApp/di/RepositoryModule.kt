package com.don.storyApp.di

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.domain.repository.ILoginRepository
import com.don.storyApp.domain.repository.LoginRepositoryImpl
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
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(api: StoryApi): ILoginRepository {
        return LoginRepositoryImpl(api)
    }
}