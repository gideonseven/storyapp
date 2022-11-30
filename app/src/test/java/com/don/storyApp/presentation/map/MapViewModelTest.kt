package com.don.storyApp.presentation.map

import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.database.FakeDao
import com.don.storyApp.domain.repository.stories.FakeStoryRepository
import com.don.storyApp.runBlockingTest
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
    private lateinit var storyDao: FakeDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: FakeStoryRepository
    private lateinit var mapViewModel: MapViewModel

    @Before
    fun setup() {
        storyDao = FakeDao()
        repository = FakeStoryRepository()
        mapViewModel = MapViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when get list from repository, should be equal list in viewModel`() {
        mainDispatcherRule.runBlockingTest {
            mapViewModel.getListLocation {
                Assert.assertEquals(it, storyDao.getStories())
            }
        }
    }
}