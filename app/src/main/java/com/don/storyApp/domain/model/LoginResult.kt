package com.don.storyApp.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

data class LoginResult(

 @field:SerializedName("name")
 val name: String? = null,

 @field:SerializedName("userId")
 val userId: String? = null,

 @field:SerializedName("token")
 val token: String? = null
)
