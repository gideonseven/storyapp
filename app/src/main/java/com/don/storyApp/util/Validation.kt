package com.don.storyApp.util

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

object Validation {

    fun isValidEmail(til: TextInputLayout): Boolean {
        var isValid: Boolean
        til.apply {
            val text = this.editText?.text.toString()
            if (text.isEmpty()) {
                error = "Tidak Boleh Kosong"
                setErrorIconDrawable(0)
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                error = "Format Email Tidak Valid"
                setErrorIconDrawable(0)
                isValid = false
            } else {
                error = null
                isValid = true
            }
            Timber.e("=== isValidEmail text $text")
            Timber.e("=== isValidEmail $isValid")
        }
        return isValid
    }


    fun isValidPassword(til: TextInputLayout): Boolean {
        var isValid: Boolean
        til.apply {
            val text = this.editText?.text.toString()
            if (text.isEmpty()) {
                til.error = "Tidak Boleh Kosong"
                setErrorIconDrawable(0)
                isValid = false
            } else if (text.length < 6) {
                til.error = "Minimum 6 Karakter"
                setErrorIconDrawable(0)
                isValid = false
            } else {
                til.error = null
                isValid = true
            }
            Timber.e("=== isValidPassword text $text")
            Timber.e("=== isValidPassword $isValid")
        }
        return isValid
    }

    fun isValidName(til: TextInputLayout): Boolean {
        var isValid: Boolean
        til.apply {
            val text = this.editText?.text.toString()
            if (text.isEmpty()) {
                til.error = "Tidak Boleh Kosong"
                setErrorIconDrawable(0)
                isValid = false
            } else if (text.length < 4) {
                til.error = "Minimum 4 Karakter"
                setErrorIconDrawable(0)
                isValid = false
            } else {
                til.error = null
                isValid = true
            }
            Timber.e("=== isValidPassword text $text")
            Timber.e("=== isValidPassword $isValid")
        }
        return isValid
    }
}
