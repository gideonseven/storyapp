package com.don.storyApp.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.util.Constant
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {
    var name: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    var email: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    var password: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)
    var isButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)

    fun submitRegister(
        errorMessage: (String) -> Unit,
        onSuccess: (SimpleNetworkModel) -> Unit
    ) {
        viewModelScope.launch {
            repository.doRegister(
                name.value.orEmpty(),
                email.value.orEmpty(),
                password.value.orEmpty()
            ).collect {
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
}