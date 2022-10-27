package com.don.storyApp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
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
}