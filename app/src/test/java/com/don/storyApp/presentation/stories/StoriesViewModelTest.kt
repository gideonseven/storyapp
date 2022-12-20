package com.don.storyApp.presentation.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.local.FakeAppPreferences
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
import com.don.storyApp.domain.repository.stories.FakeStoryRepository
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
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class StoriesViewModelTest {
    private lateinit var storiesViewModel: StoriesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockStoryRepository: FakeStoryRepository
    private lateinit var mockAuthRepository: FakeAuthRepository
    private lateinit var mockPreference: FakeAppPreferences

    @Before
    fun setup() {
        mockPreference = FakeAppPreferences()
        mockStoryRepository = FakeStoryRepository()
        mockAuthRepository = FakeAuthRepository()
        storiesViewModel = StoriesViewModel(mockStoryRepository, mockAuthRepository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `When Log Out, hasAccessToken Should be False`() = runTest {

        //given
        mockPreference.setupMockAccessToken("here is my access token")
        val expectedHasToken = false
        val actualHasToken = mockAuthRepository.hasAccessToken()

        //when
        storiesViewModel.logout()

        // then
        println("EXPECTED $expectedHasToken")
        println("ACTUAL $actualHasToken")
        Assert.assertEquals(expectedHasToken, actualHasToken)
    }
}