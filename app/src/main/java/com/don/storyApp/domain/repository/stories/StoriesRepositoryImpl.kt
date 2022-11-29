package com.don.storyApp.domain.repository.stories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.don.storyApp.data.StoryRemoteMediator
import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.data.local.database.StoryDatabase
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.don.storyApp.util.wrapEspressoIdlingResource
import com.google.gson.Gson
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
    private val preferences: AppPreferences,
    private val storyDatabase: StoryDatabase
) : IStoriesRepository {

    override suspend fun addStory(
        description: String,
        file: File,
        lat: Double,
        lon: Double
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
                file = imageMultipart,
                latitude = lat,
                longitude = lon
            )

            resource = if (response.error == false) {
                Resource.Success(data = response)
            } else {
                Resource.Error(message = response.message.orEmpty())
            }
            emit(resource)
        }
    }

    override suspend fun getPagingStories(): Flow<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, preferences),
            pagingSourceFactory = {
                wrapEspressoIdlingResource {
                    storyDatabase.storyDao().getStories()
                }
            }
        ).flow
    }

    override suspend fun getListLocation(): Flow<List<Story>> {
        return flow {
            emit(storyDatabase.storyDao().getMarkers().distinct())
        }
    }
}