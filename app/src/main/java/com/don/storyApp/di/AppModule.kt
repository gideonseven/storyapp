package com.don.storyApp.di

import android.app.Application
import com.chimerapps.niddler.core.AndroidNiddler
import com.chimerapps.niddler.interceptor.okhttp.NiddlerOkHttpInterceptor
import com.don.storyApp.StoryApplication
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.StoryApi.Companion.BASE_URL
import com.don.storyApp.domain.model.AppBuildConfig
import com.google.gson.Gson
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStoryApplication(): StoryApplication = StoryApplication()

    @Singleton
    @Provides
    fun provideAppBuildConfig(): AppBuildConfig = AppBuildConfig()

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideNiddlerOkHttp(application: Application, appBuildConfig: AppBuildConfig): NiddlerOkHttpInterceptor {
        val niddler = AndroidNiddler.Builder()
            // Use port 0 to prevent conflicting ports, auto-discovery will find it anyway!
            .setPort(0)
            // Set com.niddler.icon in AndroidManifest meta-data to an icon you wish to use for this session
            .setNiddlerInformation(AndroidNiddler.fromApplication(application))
            .setMaxStackTraceSize(10)
            .build().apply {
                if(appBuildConfig.appDebug) attachToApplication(application)
            }
        return NiddlerOkHttpInterceptor(niddler, "StoryApp")
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(niddlerOkHttpInterceptor: NiddlerOkHttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(niddlerOkHttpInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideStoryApi(retrofit: Retrofit): StoryApi = retrofit.create(StoryApi::class.java)
}