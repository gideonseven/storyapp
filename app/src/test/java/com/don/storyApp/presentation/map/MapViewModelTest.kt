package com.don.storyApp.presentation.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.model.Story
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.runBlockingTest
import com.don.storyApp.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by gideon on 14 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: IStoriesRepository
    private lateinit var mapViewModel: MapViewModel
    private val dummyStories = DataDummy.generateDummyStories()
    private var listLocation = listOf<Story>()

    @Before
    fun setup() {
        mapViewModel = MapViewModel(repository)
        listLocation = dummyStories
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when get list location from dummies should return list`() {
        mainDispatcherRule.runBlockingTest {
            val expectedData = dummyStories
            val actualData = repository.getListLocation()
            mapViewModel.getListLocation {
                Assert.assertEquals(expectedData, actualData)
            }
        }
    }
}