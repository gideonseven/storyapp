package com.don.storyApp.util

import android.util.Patterns
import com.don.storyApp.R
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
                error = this.context.getString(R.string.error_cannot_empty)
                setErrorIconDrawable(0)
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                error = this.context.getString(R.string.error_email_not_valid)
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
                error = this.context.getString(R.string.error_cannot_empty)
                setErrorIconDrawable(0)
                isValid = false
            } else if (text.length < 6) {
                error = this.context.getString(R.string.error_minimum_6_char)
                setErrorIconDrawable(0)
                isValid = false
            } else {
                error = null
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
                error = this.context.getString(R.string.error_cannot_empty)
                setErrorIconDrawable(0)
                isValid = false
            } else if (text.length < 4) {
                error = this.context.getString(R.string.error_minimum_4_char)
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
