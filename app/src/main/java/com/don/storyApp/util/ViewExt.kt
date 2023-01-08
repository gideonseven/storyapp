package com.don.storyApp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.don.storyApp.R
import com.google.android.material.snackbar.Snackbar


/**
 * Created by gideon on 28 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        setTextColor(
            ContextCompat.getColor(
                this.context,
                com.don.ui.R.color.md_white_1000
            )
        )
        show()
    }
}

fun Fragment.hideKeyboard() {
    requireActivity().currentFocus?.let { requireActivity().hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}