package com.don.storyApp.domain.repository.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.database.FakeDao
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.DataDummy
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

/**
 * Created by gideon on 14 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeStoryRepository : IStoriesRepository {
    private lateinit var storyDao: FakeDao

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        storyDao = FakeDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when data not saved, list size should be empty`() = runTest {
        val sampleList = DataDummy.generateDummyStories()
        val actualList = storyDao.getStories()
        Assert.assertNotEquals(sampleList.size, actualList.size)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when saveStories, listStory should not empty`() = runTest {
        val sampleList = DataDummy.generateDummyStories()
        storyDao.insertStory(sampleList)
        val expectedList = storyDao.getStories()
        Assert.assertEquals(sampleList, expectedList)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when delete stories, list should be empty`() = runTest {
        val sampleList = DataDummy.generateDummyStories()
        storyDao.insertStory(sampleList)
        val expectedList = storyDao.getStories()
        storyDao.deleteAll()
        Assert.assertTrue(expectedList.isEmpty())
    }

    override suspend fun addStory(
        description: String,
        file: File,
        lat: Double,
        lon: Double
    ): Flow<Resource<SimpleNetworkModel>> {
        return flow {
            storyDao.addStory(DataDummy.generateDummyStories()[0])
            val resource: Resource<SimpleNetworkModel> = Resource.Success(SimpleNetworkModel())
            emit(resource)
        }
    }

    override suspend fun getPagingStories(): Flow<PagingData<Story>> {
        return flow {
            storyDao.insertStory(DataDummy.generateDummyStories())
            emit(PagingData.from(DataDummy.generateDummyStories()))
        }
    }

    override suspend fun getListLocation(): Flow<List<Story>> {
        return flow {
            emit(storyDao.getStories())
        }
    }
}