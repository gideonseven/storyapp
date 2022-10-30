package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.google.gson.Gson
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
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
            response.onSuccess {
                resource = Resource.Success(data = this.data)
                saveToken(this.data.loginResult?.token.orEmpty())
            }.onError {
                val errorResp =
                    gson.fromJson(this.messageOrNull.orEmpty(), StoryResponse::class.java)
                resource = Resource.Error(message = errorResp.message.orEmpty())
            }.onException {
                resource = Resource.Error(message = this.exception.message.orEmpty())
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
            val response = apiService.doRegister(username, email, password)
            response.onSuccess {
                resource = Resource.Success(data = this.data)
            }.onError {
                val errorResp =
                    gson.fromJson(this.messageOrNull.orEmpty(), StoryResponse::class.java)
                resource = Resource.Error(message = errorResp.message.orEmpty())
            }.onException {
                resource = Resource.Error(message = this.exception.message.orEmpty())
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