package com.don.storyApp.domain.repository.auth

import com.don.storyApp.data.local.FakeAppPreferences
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.mockito.Mock

/**
 * Created by gideon on 21 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeAuthRepository : IAuthRepository {

    @Mock
    private lateinit var mockPreferences: FakeAppPreferences

    @Before
    fun setup() {
        mockPreferences = FakeAppPreferences()
    }

    override suspend fun doLogin(email: String, password: String): Flow<Resource<StoryResponse>> {
        return flow {
            val resource = if (email == "test.com" && password == "test") {
                Resource.Success(data = StoryResponse(error = false, message = "success"))
            } else {
                Resource.Error(message = "error")
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
            emit(Resource.Error(data = SimpleNetworkModel(message = "error"), message = "error"))
        }
    }

    override fun doLogOut() {
        mockPreferences = FakeAppPreferences()
        mockPreferences.mockAccessToken = ""
    }

    override fun saveToken(token: String) {
        mockPreferences = FakeAppPreferences()
        mockPreferences.mockAccessToken = token
    }

    override fun hasAccessToken(): Boolean {
        mockPreferences = FakeAppPreferences()
        return mockPreferences.mockAccessToken.isNotEmpty()
    }
}