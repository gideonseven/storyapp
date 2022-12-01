package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.local.FakeAppPreferences
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before

/**
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeAuthRepository : IAuthRepository {

    private lateinit var mockPreferences: FakeAppPreferences
    var responseError = ""
    var responseSuccess = ""

    @Before
    fun setup() {
        mockPreferences = FakeAppPreferences()
    }

    override suspend fun doLogin(email: String, password: String): Flow<Resource<StoryResponse>> {
        return flow {
            var resource: Resource<StoryResponse> = Resource.Loading()
            emit(resource)
            resource = Resource.Success(data = StoryResponse())
            emit(resource)
            responseError = ""
            responseSuccess = "success"
        }
    }

    override suspend fun doRegister(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<SimpleNetworkModel>> {
        return flow {
            emit(Resource.Error(data = SimpleNetworkModel(message = "error"), message = "error"))
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