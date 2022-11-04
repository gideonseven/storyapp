package com.don.storyApp.data.remote

import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.SimpleNetworkModel
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


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

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") authorization: String,
        @Query("page") currentPage: Int,
        @Query("size") pageSize: Int,
        @Query("location") location: Int = 1
    ): ApiResponse<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun doAddStory(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") latitude: Double,
        @Part("lon") longitude: Double
    ): ApiResponse<SimpleNetworkModel>

    companion object {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    }
}