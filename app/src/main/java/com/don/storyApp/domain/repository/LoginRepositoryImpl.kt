package com.don.storyApp.domain.repository

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.LoginResponse
import com.don.storyApp.util.Resource
import com.google.gson.Gson
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class LoginRepositoryImpl @Inject constructor(
    private val apiService: StoryApi,
    private val gson: Gson
) : ILoginRepository {
    override suspend fun doLogin(email: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            var resource: Resource<LoginResponse> = Resource.Loading()
            emit(resource)
            val response = apiService.doLogin(email, password)
            response.onSuccess {
                Timber.e("=== SUC")
                resource = Resource.Success(data = this.data)
            }.onError {
                Timber.e("=== ERR")
                val errorResp =
                    gson.fromJson(this.messageOrNull.orEmpty(), LoginResponse::class.java)
                resource = Resource.Error(message = errorResp.message.orEmpty())
            }.onException {
                Timber.e("=== EXXX")
                resource = Resource.Error(message = this.exception.message.orEmpty())
            }
            emit(resource)
        }
    }
}