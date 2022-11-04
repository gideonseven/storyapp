package com.don.storyApp.di

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

    @Provides
    @Singleton
    fun provideStoryApplication(): StoryApplication = StoryApplication()

    @Provides
    @Singleton
    fun provideAppBuildConfig(): AppBuildConfig = AppBuildConfig()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideNiddler(
        storyApplication: StoryApplication
    ): AndroidNiddler{
        val niddler = AndroidNiddler.Builder()
            .setPort(0) //Use port 0 to prevent conflicting ports, auto-discovery will find it anyway!
            .setNiddlerInformation(AndroidNiddler.fromApplication(storyApplication)) //Set com.niddler.icon in AndroidManifest meta-data to an icon you wish to use for this session
            .setMaxStackTraceSize(10)
            .build()
        niddler.attachToApplication(storyApplication) //Make the niddler service start whenever an activity starts
        return niddler
    }


    @Provides
    @Singleton
    fun provideNiddlerInterceptor(
        niddler: AndroidNiddler
    ): NiddlerOkHttpInterceptor{
        return NiddlerOkHttpInterceptor(niddler, "NIDDLER_STORY_APP")
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(niddlerOkHttpInterceptor: NiddlerOkHttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(niddlerOkHttpInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideStoryApi(retrofit: Retrofit): StoryApi = retrofit.create(StoryApi::class.java)
}