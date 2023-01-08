package com.don.storyApp.presentation.login

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
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {
    var email: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    var password: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    var isButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)
    val errorMessage: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    val storyResponse: MutableLiveData<StoryResponse> = MutableLiveData(StoryResponse())

    fun submitLogin(
        mail: String = email.value.orEmpty(),
        pass: String = password.value.orEmpty()
    ) {
        viewModelScope.launch {
            repository.doLogin(mail, pass).collect {
                when (it) {
                    is Resource.Success -> {
                        stateType.value = StateType.CONTENT
                        it.data?.let { response ->
                            storyResponse.value = response
                            errorMessage.value = response.message.orEmpty()
                        }
                    }
                    is Resource.Loading -> {
                        stateType.value = StateType.LOADING
                    }
                    is Resource.Error -> {
                        stateType.value = StateType.ERROR
                        errorMessage.value = it.message.orEmpty()
                    }
                }
            }
        }
    }

    fun hasAccessToken() = repository.hasAccessToken()
}