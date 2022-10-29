package com.don.storyApp.domain.repository.stories

import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Resource
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IStoriesRepository {
    suspend fun getStories(): Flow<Resource<List<Story>>>
}