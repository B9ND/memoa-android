package com.dlrjsgml.memoa.network.data.signup

import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.login.LoginResponse
import com.dlrjsgml.memoa.network.data.school.SchoolSearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GetCodeService {
    @GET("/auth/send-code")
    suspend fun sendAuthCode(@Query("email") email: String)
}

interface SendCodeService {
    @POST("/auth/verify-code")
    suspend fun checkAuthCode(@Query("email") email: String, @Query("code") code: String)
}

interface LastSignupService {
    @POST("/auth/register")
    suspend fun lastSignupSearch(@Body register: SignUpRequest): SignUpResponse
}