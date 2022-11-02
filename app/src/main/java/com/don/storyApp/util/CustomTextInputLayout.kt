package com.don.storyApp.util

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData
import com.don.storyApp.R
import com.google.android.material.textfield.TextInputLayout


/**
 * Created by gideon on 03 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class CustomTextInputLayout : TextInputLayout {

    private val isValidField: MutableLiveData<Boolean> = MutableLiveData(false)

    var validationType = ValidationType.NONE

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        this.apply {
            addOnEditTextAttachedListener {
                editText?.doAfterTextChanged { editable ->
                    editable?.let {
                        when (validationType) {
                            ValidationType.EMAIL -> isValidEmail(it)
                            ValidationType.PASSWORD -> isValidPassword(it)
                            ValidationType.DEFAULT -> isValidName(it)
                            ValidationType.NONE -> {
                                // do nothing
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(editable: Editable) {
        if (editable.isEmpty()) {
            this@CustomTextInputLayout.error = this.context.getString(R.string.error_cannot_empty)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editable).matches()) {
            this@CustomTextInputLayout.error =
                this.context.getString(R.string.error_email_not_valid)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else {
            this@CustomTextInputLayout.error = null
            this@CustomTextInputLayout.isValidField.value = true
        }
        isFormValid()
    }

    private fun isValidPassword(editable: Editable) {
        if (editable.isEmpty()) {
            this@CustomTextInputLayout.error = this.context.getString(R.string.error_cannot_empty)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else if (editable.length < 6) {
            this@CustomTextInputLayout.error = this.context.getString(R.string.error_minimum_6_char)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else {
            this@CustomTextInputLayout.error = null
            this@CustomTextInputLayout.isValidField.value = true
        }
        isFormValid()
    }

    private fun isValidName(editable: Editable) {
        if (editable.isEmpty()) {
            this@CustomTextInputLayout.error = this.context.getString(R.string.error_cannot_empty)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else if (editable.length < 4) {
            this@CustomTextInputLayout.error = this.context.getString(R.string.error_minimum_4_char)
            setErrorIconDrawable(0)
            this@CustomTextInputLayout.isValidField.value = false
        } else {
            this@CustomTextInputLayout.error = null
            this@CustomTextInputLayout.isValidField.value = true
        }
        isFormValid()
    }

    fun isFormValid() = this@CustomTextInputLayout.isValidField.value ?: false
}