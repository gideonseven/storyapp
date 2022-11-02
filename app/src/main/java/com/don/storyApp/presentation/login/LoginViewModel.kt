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

    fun submitLogin(
        errorMessage: (String) -> Unit,
        onSuccess: (StoryResponse) -> Unit
    ) {
        viewModelScope.launch {
            repository.doLogin(email.value.orEmpty(), password.value.orEmpty()).collect {
                when (it) {
                    is Resource.Success -> {
                        stateType.value = StateType.CONTENT
                        it.data?.let(onSuccess)
                    }
                    is Resource.Loading -> {
                        stateType.value = StateType.LOADING
                    }
                    is Resource.Error -> {
                        stateType.value = StateType.ERROR
                        errorMessage(it.message.orEmpty())
                    }
                }
            }
        }
    }

    fun hasAccessToken() = repository.hasAccessToken()
}