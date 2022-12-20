package com.don.storyApp.presentation.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.database.FakeDao
import com.don.storyApp.domain.repository.stories.FakeStoryRepository
import com.don.storyApp.util.StateType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
@ExperimentalCoroutinesApi
class MapViewModelTest {
    private lateinit var mapViewModel: MapViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: FakeStoryRepository
    private lateinit var storyDao: FakeDao

    @Before
    fun setup() {
        storyDao = FakeDao()
        repository = FakeStoryRepository()
        mapViewModel = MapViewModel(repository)
    }

    @Test
    fun `when List Map from Database is not empty Should Return StateTypeContent`() = runTest {
        //given
        val expectedState = StateType.CONTENT
        val actualState: MutableLiveData<StateType> = MutableLiveData()

        //when
        mapViewModel.getListLocation()
        actualState.value = mapViewModel.stateType.value

        //then
        println("EXPECTED $expectedState")
        println("ACTUAL ${actualState.value}")
        println("LIST STORY SIZE ${mapViewModel.listStory.value?.size}")
        Assert.assertEquals(expectedState, actualState.value)
    }
}