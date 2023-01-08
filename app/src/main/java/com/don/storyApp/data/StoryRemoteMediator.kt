package com.don.storyApp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.data.local.database.StoryDatabase
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.domain.model.Story
import com.skydoves.sandwich.isError
import com.skydoves.sandwich.isException
import com.skydoves.sandwich.isFailure
import com.skydoves.sandwich.onSuccess

/**
 * Created by gideon on 05 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: StoryDatabase,
    private val apiService: StoryApi,
    private val appPreferences: AppPreferences
) : RemoteMediator<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Story>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData: ArrayList<Story> = arrayListOf()
            val response = apiService.getStories(
                "Bearer ${appPreferences.accessToken.orEmpty()}",
                page,
                state.config.pageSize
            )
            response.onSuccess {
                this.data.listStory?.let {
                    // check valid lat lon only
                    responseData.addAll(validLatLonStories(it))
                }
            }

            val endOfPaginationReached = responseData.isEmpty()

            if (response.isError || response.isException || response.isFailure) {
                return MediatorResult.Error(Exception())
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.storyDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    com.don.database.database.RemoteKeys(
                        id = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                database.storyDao().insertStory(responseData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Story>): com.don.database.database.RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Story>): com.don.database.database.RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Story>): com.don.database.database.RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    /**
     *   add to list when valid lat lon only
     *
     *   valid latitude values in the range of [-90, 90]
     *   valid longitude values in the range of [-180, 180]
     */
    private fun validLatLonStories(stories: List<Story>): List<Story> {
        return stories.filter { story -> story.lat >= -90 && story.lat <= 90.0 && story.lon >= -180 && story.lon <= 180 }
    }
}