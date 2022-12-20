package com.don.storyApp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
import com.don.storyApp.domain.repository.auth.IAuthRepository
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
 * Created by gideon on 18 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RegisterViewModelTest {
    private lateinit var registerViewModel: RegisterViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAuthRepository

    @Before
    fun setup() {
        repository = FakeAuthRepository()
        registerViewModel = RegisterViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when Register With Wrong Value Should Return StateTypeError`() = runTest {
        //given
        val expectedState = StateType.ERROR
        val actualState: MutableLiveData<StateType> = MutableLiveData()

        //when
        registerViewModel.submitRegister()
        actualState.value = registerViewModel.stateType.value

        //then
        println("EXPECTED $expectedState")
        println("ACTUAL ${actualState.value}")
        Assert.assertEquals(expectedState, actualState.value)
    }
}