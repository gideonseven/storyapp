package com.don.storyApp.domain.repository.stories

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.data.storage.AppPreferences
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Resource
import com.google.gson.Gson
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
}