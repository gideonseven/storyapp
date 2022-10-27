package com.don.storyApp.data.remote.dto

import com.don.storyApp.domain.model.LoginResult
import com.don.storyApp.util.BaseNetworkModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,
    @field:SerializedName("error")
    override val error: Boolean?,
    @field:SerializedName("message")
    override val message: String?
) : BaseNetworkModel