package com.don.storyApp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.repository.ILoginRepository
import com.don.storyApp.util.Resource
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ILoginRepository
) : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    val mIsValidEmail: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidEmail = mIsValidEmail as LiveData<Boolean>

    val mIsValidPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidPassword = mIsValidPassword as LiveData<Boolean>

    //    private val mIsButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mIsButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(true)
    val isButtonEnabled = mIsButtonEnabled as LiveData<Boolean>

    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    fun checkForm() {
//        mIsButtonEnabled.value = isValidEmail.value == true && isValidPassword.value == true
        mIsButtonEnabled.value = true
    }

    fun submitLogin() {
        viewModelScope.launch {
            repository.doLogin("abcdeflf@hotmail.com", "aaaaaaaa").collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.e("== RESPONSE Success")
                        Timber.e(
                            "== RESPONSE ${
                                it.data
                            }"
                        )
                        stateType.value = StateType.CONTENT
                    }
                    is Resource.Loading -> {
                        Timber.e("== RESPONSE Loading")
                        stateType.value = StateType.LOADING
                    }
                    is Resource.Error -> {
                        Timber.e("== RESPONSE Error")
                        Timber.e(
                            "== RESPONSE ${
                                it.message
                            }"
                        )
                        stateType.value = StateType.ERROR
                    }
                }
            }
        }
    }
}