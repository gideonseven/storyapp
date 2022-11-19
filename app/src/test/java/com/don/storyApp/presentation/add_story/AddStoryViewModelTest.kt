package com.don.storyApp.presentation.add_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File


/**
 * Created by gideon on 19 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IStoriesRepository
    private lateinit var addStoryViewModel: AddStoryViewModel


    @Before
    fun setup() {

        addStoryViewModel = AddStoryViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when add story without description will return error message`() {

        mainDispatcherRule.runBlockingTest {
            val file = File("")
            val expectedErrorMessage = "error"
            var actualErrorMessage = ""
            val lat = 0.1
            val lon = 0.2

            addStoryViewModel.addStory(errorMessage = {
                actualErrorMessage = it
            }, onSuccess = {})

            Mockito.verify(repository.addStory("", file, lat, lon))
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
        }
    }
}