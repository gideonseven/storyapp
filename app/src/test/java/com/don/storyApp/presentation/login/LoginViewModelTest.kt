package com.don.storyApp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.runBlockingTest
import com.don.storyApp.util.DataDummy
import com.don.storyApp.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mockAuthRepository: FakeAuthRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockAuthRepository = FakeAuthRepository()
        loginViewModel = LoginViewModel(mockAuthRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Login With Wrong Credential Should Return Error Message`() {

        mainDispatcherRule.runBlockingTest {
            val expectedErrorMessage = "error"
            var actualErrorMessage = ""

            loginViewModel.submitLogin(errorMessage = {
                actualErrorMessage = it
            }, onSuccess = {})

            Mockito.verify(mockAuthRepository.doLogin("", ""))
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When login credential true state type equals`() {
        mainDispatcherRule.runBlockingTest {
            //given
            var resourceError = false
            var rsType = false

            //when
            Mockito.`when`(mockAuthRepository.doLogin("xyz@test.com", "1234567A")).thenReturn(flow {
                resourceError = Resource.Success(data = StoryResponse()).data?.error == true
                emit(Resource.Success(data = StoryResponse()))
            })
            loginViewModel.submitLogin(errorMessage = {}, onSuccess = {
                rsType = it.error == true
            })

            //then
            Assert.assertEquals(resourceError, rsType)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When login credential false state type not equals`() {
        mainDispatcherRule.runBlockingTest {
            //given
            var resourceError = false
            var rsType = false

            //when
            Mockito.`when`(mockAuthRepository.doLogin("xy@est.com", "1237A")).thenReturn(flow {
                resourceError = Resource.Success(data = StoryResponse()).data?.error == false
                emit(Resource.Success(data = StoryResponse()))
            })
            loginViewModel.submitLogin(errorMessage = {}, onSuccess = {
                rsType = it.error == true
            })

            //then
            Assert.assertEquals(resourceError, rsType)
        }
    }
}