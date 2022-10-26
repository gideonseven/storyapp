package com.don.storyApp.data.remote.dto

import com.don.storyApp.domain.model.LoginResult
import com.don.storyApp.util.BaseNetworkModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
	@Json(name="loginResult")
	val loginResult: LoginResult? = null,
	override val error: Boolean?,
	override val message: String?
): BaseNetworkModel