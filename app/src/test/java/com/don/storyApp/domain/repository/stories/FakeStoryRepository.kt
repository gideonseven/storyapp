package com.don.storyApp.domain.repository.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.database.FakeDao
import com.don.storyApp.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by gideon on 14 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeStoryRepository {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var storyDao: FakeDao

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
}