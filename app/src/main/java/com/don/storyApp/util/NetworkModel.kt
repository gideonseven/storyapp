package com.don.storyApp.util

import com.google.gson.annotations.SerializedName

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

interface BaseNetworkModel {
    val error: Boolean?
    val message: String?
}

data class SimpleNetworkModel(
    @field:SerializedName("message") override val message: String? = null,
    @field:SerializedName("error") override val error: Boolean? = null
) : BaseNetworkModel