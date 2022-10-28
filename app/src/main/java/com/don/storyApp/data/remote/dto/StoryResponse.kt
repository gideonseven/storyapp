package com.don.storyApp.data.remote.dto

import com.don.storyApp.domain.model.LoginResult
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.BaseNetworkModel
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,
    @field:SerializedName("listStory")
    val listStory: List<Story>? = null,
    @field:SerializedName("error")
    override val error: Boolean? = null,
    @field:SerializedName("message")
    override val message: String? = null
) : BaseNetworkModel