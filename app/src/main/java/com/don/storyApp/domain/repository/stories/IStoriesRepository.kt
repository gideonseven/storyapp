package com.don.storyApp.domain.repository.stories

import androidx.paging.PagingData
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IStoriesRepository {
    suspend fun addStory(description: String, file: File): Flow<Resource<SimpleNetworkModel>>
    suspend fun getPagingStories(): Flow<PagingData<Story>>
}