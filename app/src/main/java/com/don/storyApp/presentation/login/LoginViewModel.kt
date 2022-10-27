package com.don.storyApp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.repository.ILoginRepository
import com.don.storyApp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val mIsButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled = mIsButtonEnabled as LiveData<Boolean>

    fun checkForm() {
       mIsButtonEnabled.value = isValidEmail.value == true && isValidPassword.value == true
    }
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun submitLogin(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repository.doLogin(email.value.orEmpty(), password.value.orEmpty())
                .collect{ resource ->
                    when(resource ){
                        is Resource.Success -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }
}