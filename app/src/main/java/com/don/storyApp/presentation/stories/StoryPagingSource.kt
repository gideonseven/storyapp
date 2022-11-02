package com.don.storyApp.presentation.stories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.domain.model.Story
import com.google.gson.Gson
import com.skydoves.sandwich.onSuccess

/**
 * Created by gideon on 02 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

class StoryPagingSource(
    private val apiService: StoryApi,
    private val preferences: AppPreferences,
    private val gson: Gson
) :
    PagingSource<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            var listStory = listOf<Story>()
            val response = apiService.getStories(
                authorization = "Bearer ${preferences.accessToken.orEmpty()}",
                currentPage = page,
                location = 1
            )

            response.onSuccess {
                this.data.listStory?.let {
                    listStory = it
                    saveStory(it)
                }
            }

            LoadResult.Page(
                data = listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun saveStory(listStory: List<Story>) {
        val stringListStory = gson.toJson(listStory).toString()
        preferences.listStory = stringListStory
    }
}