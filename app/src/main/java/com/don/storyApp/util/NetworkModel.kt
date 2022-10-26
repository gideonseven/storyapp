package com.don.storyApp.util

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

interface BaseNetworkModel {
    val error: Boolean?
    val message: String?
}

@JsonClass(generateAdapter = true)
data class SimpleNetworkModel(
    @Json(name = "message") override val message: String? = null,
    @Json (name = "error") override val error: Boolean? = null
) : BaseNetworkModel