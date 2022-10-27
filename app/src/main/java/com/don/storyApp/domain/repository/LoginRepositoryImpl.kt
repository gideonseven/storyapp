package com.don.storyApp.domain.repository

import com.don.storyApp.data.remote.StoryApi
import com.don.storyApp.data.remote.dto.LoginResponse
import com.don.storyApp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class LoginRepositoryImpl @Inject constructor(
    private val apiService: StoryApi
) : ILoginRepository{
    override suspend fun doLogin(email: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading())
            val response = apiService.doLogin(email, password)
            if(response.error == true ){
                emit(Resource.Error(message = response.message.orEmpty()))
            }else {
                emit(Resource.Success(data = response))
            }
        }
    }
}