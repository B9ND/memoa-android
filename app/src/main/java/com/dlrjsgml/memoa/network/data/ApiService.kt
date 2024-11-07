package com.dlrjsgml.memoa.network.data

import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.dlrjsgml.memoa.network.data.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/auth/send-code")
    suspend fun sendAuthCode(@Query("email") email: String)

    @POST("/auth/verify-code")
    suspend fun checkAuthCode(@Query("email") email: String, @Query("code") code: String)
}
