package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.local.FakeAppPreferences
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeAuthRepository : IAuthRepository {

    private var mockPreferences = FakeAppPreferences()

    override suspend fun doLogin(email: String, password: String): Flow<Resource<StoryResponse>> {
        return flow {
            val resource: Resource<StoryResponse>
            if (email == "test.com" && password == "test") {
                resource = Resource.Success(
                    data = StoryResponse(
                        error = false,
                        message = "success"
                    )
                )
                saveToken("i have access token")
            } else {
                resource = Resource.Error(
                    message = "error"
                )
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
            val resource = Resource.Error(data = SimpleNetworkModel(), message = "error")
            emit(resource)
        }
    }

    override fun doLogOut() {
        mockPreferences.mockAccessToken = ""
    }

    override fun saveToken(token: String) {
        mockPreferences.mockAccessToken = token
    }

    override fun hasAccessToken(): Boolean {
        return mockPreferences.mockAccessToken.isNotEmpty()
    }
}