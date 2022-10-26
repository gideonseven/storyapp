package com.don.storyApp.domain.usecase

import com.don.storyApp.data.remote.dto.LoginResponse
import com.don.storyApp.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by gideon on 21 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ILoginUseCase {
    fun doLogin(email: String, password: String): Flow<Resource<LoginResponse>>
}