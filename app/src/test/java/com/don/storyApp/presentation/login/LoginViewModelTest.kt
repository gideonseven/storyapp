package com.don.storyApp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
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
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mockAuthRepository: FakeAuthRepository

    @Before
    fun setup() {
        mockAuthRepository = FakeAuthRepository()
        loginViewModel = LoginViewModel(mockAuthRepository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Test
    fun `when Login With Wrong Credential Should Return Error Message`() = runTest {
        val expectedErrorMessage = "error"
        loginViewModel.submitLogin(errorMessage = {
            Assert.assertEquals(expectedErrorMessage, it)
        }, onSuccess = {})
    }

    @Test
    fun `When login credential true state type equals`() = runTest {
        //given
        val resourceError = false

        //when
        loginViewModel.submitLogin(
            mail = "test.com",
            pass = "test",
            errorMessage = {},
            onSuccess = {
                //then
                Assert.assertEquals(resourceError, it.error)
            })
    }
}