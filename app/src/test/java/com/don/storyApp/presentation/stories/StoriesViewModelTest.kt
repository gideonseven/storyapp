package com.don.storyApp.presentation.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.FakeAppPreferences
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
import com.don.storyApp.domain.repository.stories.FakeStoryRepository
import com.don.storyApp.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
class StoriesViewModelTest {
    private lateinit var mockStoryRepository: FakeStoryRepository
    private lateinit var mockAuthRepository: FakeAuthRepository
    private lateinit var mockPreference: FakeAppPreferences
    private lateinit var storiesViewModel: StoriesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockPreference = FakeAppPreferences()
        mockStoryRepository = FakeStoryRepository()
        mockAuthRepository = FakeAuthRepository()
        storiesViewModel = StoriesViewModel(mockStoryRepository, mockAuthRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When Log Out, hasAccessToken Should be False`() {
        mainDispatcherRule.runBlockingTest {
            // set initial value to fake pref
            mockPreference.setupMockAccessToken("here is my access token")

            // set expected boolean to variable
            val expectedHasToken = false

            // get actual boolean from repository
            val actualHasToken = mockAuthRepository.hasAccessToken()

            storiesViewModel.logout()

            //verify it
            Mockito.verify(mockAuthRepository.doLogOut())

            // assertion
            Assert.assertEquals(actualHasToken, expectedHasToken)
        }
    }
}