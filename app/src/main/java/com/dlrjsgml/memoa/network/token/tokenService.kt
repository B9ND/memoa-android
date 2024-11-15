package com.dlrjsgml.memoa.network.token

import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenService {
    @POST("/auth/reissue")
    suspend fun token(@Body accTokenRequest: AccTokenRequest): AccTokenResponse
}