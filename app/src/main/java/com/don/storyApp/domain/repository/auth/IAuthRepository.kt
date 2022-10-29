package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IAuthRepository {
    suspend fun doLogin(email: String, password: String): Flow<Resource<StoryResponse>>
    suspend fun doRegister(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<SimpleNetworkModel>>

    fun doLogOut()
    fun saveToken(token: String)
    fun hasAccessToken(): Boolean
}