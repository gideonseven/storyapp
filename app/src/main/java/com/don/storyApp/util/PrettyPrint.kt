package com.don.storyApp.util

import android.os.Bundle
import com.google.gson.GsonBuilder
import org.jetbrains.annotations.NotNull
import timber.log.Timber


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
fun prettyPrinting(@NotNull any: Any){
    Timber.e("=== Pretty Printing ${GsonBuilder().setPrettyPrinting().create().toJson(any)}")
}

fun Bundle.logValue(){
    for (key in this.keySet()) {
        Timber.e("KEY ==> $key  : VALUE ==>  ${this.get(key)}")
    }
}