package com.don.storyApp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
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
    fun `When Login With Wrong Credential Should Return StateTypeError`() = runTest {
        //given
        val expectedState = StateType.ERROR
        val actualState: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

        //when
        loginViewModel.submitLogin("", "")
        actualState.value = loginViewModel.stateType.value

        //then
        println("ACTUAL ${actualState.value}")
        println("EXPECTED $expectedState")
        Assert.assertEquals(expectedState, actualState.value)
    }

    @Test
    fun `When Login With Correct Credential Should Return StateTypeContent`() = runTest {
        //given
        val expectedState = StateType.CONTENT
        val actualState: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

        //when
        loginViewModel.submitLogin(
            mail = "test.com",
            pass = "test",
        )
        actualState.value = loginViewModel.stateType.value

        //then
        println("ACTUAL ${actualState.value}")
        println("EXPECTED $expectedState")
        Assert.assertEquals(expectedState, actualState.value)
    }
}