package com.don.storyApp.util

import android.view.View
import androidx.core.content.ContextCompat
import com.don.storyApp.R
import com.google.android.material.snackbar.Snackbar


/**
 * Created by gideon on 28 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.md_white_1000
            )
        )
        show()
    }
}