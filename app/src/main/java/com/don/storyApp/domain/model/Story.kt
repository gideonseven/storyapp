package com.don.storyApp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Parcelize
data class Story(

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("lon")
    val lon: Double = 0.0,

    @field:SerializedName("lat")
    val lat: Double = 0.0
) : Parcelable