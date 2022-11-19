package com.don.storyApp.presentation.register

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.don.storyApp.MainDispatcherRule
import com.don.storyApp.domain.repository.auth.IAuthRepository
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


/**
 * Created by gideon on 18 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAuthRepository
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Register With Wrong Value Should Return Error Message`() {

        mainDispatcherRule.runBlockingTest {
            val expectedErrorMessage = "error"
            var actualErrorMessage = ""

            registerViewModel.submitRegister(errorMessage = {
                actualErrorMessage = it
            }, onSuccess = {})

            Mockito.verify(repository.doRegister("", "", ""))
            Log.e("TAGGGGG", "== $expectedErrorMessage == $actualErrorMessage ==")
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
        }
    }
}