package com.don.storyApp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.don.storyApp.util.TextType
import com.don.storyApp.util.isTextValid
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(){
    private val _isButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val isButtonEnabled = _isButtonEnabled as LiveData<Boolean>


    fun validateForm(email: TextInputLayout, password: TextInputLayout){
        _isButtonEnabled.value  = email.isTextValid(TextType.EMAIL) && password.isTextValid(TextType.PASSWORD)
    }
}