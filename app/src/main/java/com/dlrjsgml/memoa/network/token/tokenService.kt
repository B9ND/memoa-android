package com.dlrjsgml.memoa.network.token

import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("/auth/reissue")
    suspend fun token(@Body accTokenRequest: AccTokenRequest): AccTokenResponse
}