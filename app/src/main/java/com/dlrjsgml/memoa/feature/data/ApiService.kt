package com.dlrjsgml.memoa.feature.data

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.dlrjsgml.memoa.feature.data.LoginResponse


interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
