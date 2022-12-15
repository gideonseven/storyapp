package com.don.storyApp.presentation.add_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.don.storyApp.MainDispatcherRule
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
 * Created by gideon on 19 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addStoryViewModel: AddStoryViewModel
    private lateinit var repository: FakeStoryRepository


    @Before
    fun setup() {
        repository = FakeStoryRepository()
        addStoryViewModel = AddStoryViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Test
    fun `when add story without description will return StateTypeError`() = runTest {
        //given
        val expectedState = StateType.ERROR
        val actualState: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

        //when
        addStoryViewModel.addStoryDummy()
        actualState.value = addStoryViewModel.stateType.value

        //then
        println("ACTUAL ${actualState.value}")
        println("EXPECTED $expectedState")
        Assert.assertEquals(actualState.value, expectedState)
    }
}