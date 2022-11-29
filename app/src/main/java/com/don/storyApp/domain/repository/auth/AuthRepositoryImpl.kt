package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class AuthRepositoryImpl @Inject constructor(
    private val apiService: StoryApi,
    private val gson: Gson,
    private val preferences: AppPreferences
) : IAuthRepository {
    override suspend fun doLogin(email: String, password: String): Flow<Resource<StoryResponse>> {
        return flow {
            var resource: Resource<StoryResponse> = Resource.Loading()
            emit(resource)
            val response = apiService.doLogin(email, password)

            if (response.error == false) {
                resource = Resource.Success(data = response)
                saveToken(response.loginResult?.token.orEmpty())
            } else {
                resource = Resource.Error(message = response.message ?: "")
            }
            emit(resource)
        }
    }

    override suspend fun doRegister(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<SimpleNetworkModel>> {
        return flow {
            var resource: Resource<SimpleNetworkModel> = Resource.Loading()
            emit(resource)
            val response = apiService.doRegister(
                email = email,
                name = username,
                password = password
            )

            resource = if (response.error == false) {
                Resource.Success(data = SimpleNetworkModel())
            } else {
                Resource.Error(message = response.message.orEmpty())
            }
            emit(resource)
        }
    }

    override fun doLogOut() {
        preferences.clear()
    }

    override fun saveToken(token: String) {
        preferences.accessToken = token
    }

    override fun hasAccessToken() = preferences.accessToken?.isNotEmpty() ?: false
}