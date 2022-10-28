package com.don.storyApp.data.remote

import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface StoryApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun doLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<StoryResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun doRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): ApiResponse<SimpleNetworkModel>

    @FormUrlEncoded
    @POST("description")
    suspend fun doAddStory(
        @Field("description") symbol: String,
        @Field("photo") photoFile: ResponseBody
    ): Resource<StoryResponse>

    @FormUrlEncoded
    @POST("description")
    suspend fun getStories(
        @Field("description") symbol: String,
        @Field("photo") photoFile: ResponseBody
    ): Resource<StoryResponse>

    @FormUrlEncoded
    @POST("description")
    suspend fun getStory(
        @Field("description") symbol: String,
        @Field("photo") photoFile: ResponseBody
    ): Resource<StoryResponse>

    companion object {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    }
}