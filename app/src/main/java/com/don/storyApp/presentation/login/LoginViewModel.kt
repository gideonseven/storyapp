package com.don.storyApp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.util.Constant
import com.don.storyApp.util.Resource
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    var password: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)

    val mIsValidEmail: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidEmail = mIsValidEmail as LiveData<Boolean>

    val mIsValidPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidPassword = mIsValidPassword as LiveData<Boolean>

    private val mIsButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled = mIsButtonEnabled as LiveData<Boolean>

    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    fun checkForm() {
//        mIsButtonEnabled.value = isValidEmail.value == true && isValidPassword.value == true
        mIsButtonEnabled.value = true
    }

    fun submitLogin(
        errorMessage: (String) -> Unit,
        onSuccess: (StoryResponse) -> Unit
    ) {
        viewModelScope.launch {
//            repository.doLogin(email.value.orEmpty(), password.value.orEmpty()).collect {
            repository.doLogin("xyz@test.com", "1234567A").collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.e("== RESPONSE Success")
                        Timber.e(
                            "== RESPONSE ${it.data}"
                        )
                        stateType.value = StateType.CONTENT
                        it.data?.let(onSuccess)
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
                        errorMessage(it.message.orEmpty())
                    }
                }
            }
        }
    }

    fun hasAccessToken() = repository.hasAccessToken()
}