package com.don.storyApp.domain.model

import com.squareup.moshi.Json

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

data class LoginResult(

 @Json(name="name")
 val name: String? = null,

 @Json(name="userId")
 val userId: String? = null,

 @Json(name="token")
 val token: String? = null
)
