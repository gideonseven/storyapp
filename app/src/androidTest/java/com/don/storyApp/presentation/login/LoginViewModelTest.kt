package com.don.storyApp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.presentation.MainDispatcherRule
import com.don.storyApp.presentation.runBlockingTest
import com.don.storyApp.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    private val repository = mock(IAuthRepository::class.java)

    @Before
    fun setUp() {
        viewModel = LoginViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun When_login_credential_true_state_type_equals() {
        mainDispatcherRule.runBlockingTest {
            //given
            var resourceError = false
            var rsType = false

            //when
            Mockito.`when`(repository.doLogin("xyz@test.com", "1234567A")).thenReturn(flow {
                resourceError = Resource.Success(data = StoryResponse()).data?.error == true
                emit(Resource.Success(data = StoryResponse()))
            })
            viewModel.submitLogin(errorMessage = {}, onSuccess = {
                rsType = it.error == true
            })

            //then
            Assert.assertEquals(resourceError, rsType)
        }
    }

    @Test
    fun When_login_credential_false_state_type_not_equals() {
        mainDispatcherRule.runBlockingTest {
            //given
            var resourceError = false
            var rsType = false

            //when
            Mockito.`when`(repository.doLogin("xy@est.com", "1237A")).thenReturn(flow {
                resourceError = Resource.Success(data = StoryResponse()).data?.error == false
                emit(Resource.Success(data = StoryResponse()))
            })
            viewModel.submitLogin(errorMessage = {}, onSuccess = {
                rsType = it.error == true
            })

            //then
            Assert.assertEquals(resourceError, rsType)
        }
    }
}