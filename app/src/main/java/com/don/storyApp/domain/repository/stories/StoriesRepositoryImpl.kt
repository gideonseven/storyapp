package com.don.storyApp.domain.repository.stories

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.data.storage.AppPreferences
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.google.gson.Gson
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class StoriesRepositoryImpl @Inject constructor(
    private val apiService: StoryApi,
    private val gson: Gson,
    private val preferences: AppPreferences
) : IStoriesRepository {

    override suspend fun getStories(): Flow<Resource<List<Story>>> {
        return flow {
            var resource: Resource<List<Story>> = Resource.Loading()
            emit(resource)
            val response = apiService.getStories("Bearer ${preferences.accessToken.orEmpty()}")
            response.onSuccess {
                resource = Resource.Success(data = this.data.listStory.orEmpty())
            }.onError {
                val errorResp =
                    gson.fromJson(this.messageOrNull.orEmpty(), StoryResponse::class.java)
                resource = Resource.Error(message = errorResp.message.orEmpty())
            }.onException {
                resource = Resource.Error(message = this.exception.message.orEmpty())
            }
            emit(resource)
        }
    }

    override suspend fun addStory(
        description: String,
        file: File
    ): Flow<Resource<SimpleNetworkModel>> {
        return flow {
            var resource: Resource<SimpleNetworkModel> = Resource.Loading()
            emit(resource)

            val desc = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val response = apiService.doAddStory(
                authorization = "Bearer ${preferences.accessToken.orEmpty()}",
                description = desc,
                file = imageMultipart
            )
            response.onSuccess {
                resource = Resource.Success(data = this.data)
            }.onError {
                val errorResp =
                    gson.fromJson(this.messageOrNull.orEmpty(), StoryResponse::class.java)
                resource = Resource.Error(message = errorResp.message.orEmpty())
            }.onException {
                resource = Resource.Error(message = this.exception.message.orEmpty())
            }
            emit(resource)
        }
    }
}