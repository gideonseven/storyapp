package com.don.storyApp.util

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
 class Success<T>(data: T) : Resource<T>(data)
 class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
 class Loading<T> : Resource<T>()
}