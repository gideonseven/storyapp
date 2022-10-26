package com.don.storyApp.util

import android.util.Patterns
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


/**
 * Created by gideon on 24 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */


@BindingAdapter("textType")
fun TextInputLayout.isTextValid(textType: TextType): Boolean {
    var isValid = false
    editText?.doAfterTextChanged {
        when (textType) {
            TextType.EMAIL -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                    error =  "Invalid Email Address"
                    setErrorIconDrawable(0)
                    isValid = false
                } else if (it.toString().isBlank()) {
                    error = "Tidak Boleh Kosong"
                    setErrorIconDrawable(0)
                    isValid = false
                } else {
                    error =  null
                    isValid = true
                }
            }

            TextType.DEFAULT -> {
               if (it.toString().isEmpty()) {
                   error =   "Tidak Boleh Kosong"
                   setErrorIconDrawable(0)
                   isValid = false
                } else {
                   error =   null
                   isValid = true
                }
            }

            TextType.PASSWORD -> {
                if (it.toString().length < 8) {
                    error = "Minimum 8 Karakter"
                    setErrorIconDrawable(0)
                    isValid = false
                }
                if (!it.toString().matches(".*[A-Z].*".toRegex())) {
                    error = "Harus memiliki 1 Upper Case"
                    setErrorIconDrawable(0)
                    isValid = false
                }
                if (!it.toString().matches(".*[a-z].*".toRegex())) {
                    error = "Harus memiliki 1 Lower Case"
                    setErrorIconDrawable(0)
                    isValid = false
                }
                if (!it.toString().matches(".*[@#\$%^&+=].*".toRegex())) {
                    error = "Harus memiliki Spesial Karakter (@#\$%^&+=)"
                    setErrorIconDrawable(0)
                    isValid = false
                }
            }
        }
    }
    return isValid
}