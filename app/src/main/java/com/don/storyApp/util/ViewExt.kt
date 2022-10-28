package com.don.storyApp.util

import android.view.View


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