package com.don.storyApp.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.don.storyApp.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*


/**
 * Created by gideon on 14 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class StoryDaoTest{

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StoryDatabase
    private lateinit var dao: StoryDao
    private val sampleStories = DataDummy.generateDummyStories()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).build()
        dao = database.storyDao()
    }

    @After
    fun closeDb() = database.close()

/*    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveNews_Success() = runBlockingTest {
        dao.insertStory(sampleStories)
        val actualNews = dao.getStories()
        Assert.assertEquals(sampleStories.title, actualNews[0].title)
        Assert.assertTrue(dao.isNewsBookmarked(sampleStories.title).getOrAwaitValue())
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNews_Success() = runBlockingTest {
        dao.insertStory(sampleStories)
        dao.deleteNews(sampleStories.title)
        val actualNews = dao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.isEmpty())
        Assert.assertFalse(dao.isNewsBookmarked(sampleStories.title).getOrAwaitValue())
    }*/
}