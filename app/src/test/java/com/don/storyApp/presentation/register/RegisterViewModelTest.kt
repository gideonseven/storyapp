package com.don.storyApp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.auth.FakeAuthRepository
import com.don.storyApp.domain.repository.auth.IAuthRepository
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
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAuthRepository
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        repository = FakeAuthRepository()
        registerViewModel = RegisterViewModel(repository)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Test
    fun `when Register With Wrong Value Should Return Error Message`() = runTest {
        val expectedErrorMessage = "error"
        var actualErrorMessage = ""
        registerViewModel.submitRegister(errorMessage = {
            actualErrorMessage = it
            println("ACTUAL $actualErrorMessage")
            println("EXPECTED $expectedErrorMessage")
        }, onSuccess = {})

        Assert.assertEquals(expectedErrorMessage, actualErrorMessage)

    }
}