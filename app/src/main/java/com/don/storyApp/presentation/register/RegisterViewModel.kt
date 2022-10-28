package com.don.storyApp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.repository.IAuthRepository
import com.don.storyApp.util.Constant
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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

    val mIsValidName: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidName = mIsValidName as LiveData<Boolean>

    val mIsValidEmail: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidEmail = mIsValidEmail as LiveData<Boolean>

    val mIsValidPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isValidPassword = mIsValidPassword as LiveData<Boolean>

    private val mIsButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled = mIsButtonEnabled as LiveData<Boolean>

    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    fun checkForm() {
        mIsButtonEnabled.value =
            isValidName.value == true && isValidEmail.value == true && isValidPassword.value == true
    }

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
}