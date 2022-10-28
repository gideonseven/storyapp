package com.don.storyApp.domain.repository

import com.don.storyApp.data.remote.dto.LoginResponse
import com.don.storyApp.util.Resource
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 27 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IAuthRepository {
    suspend fun doLogin(email: String, password: String): Flow<Resource<LoginResponse>>
    suspend fun doRegister(username: String, email: String, password: String): Flow<Resource<LoginResponse>>
    fun doLogOut()
    fun saveToken(token: String)
}