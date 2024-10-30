package com.dlrjsgml.memoa.network.profile

import retrofit2.http.GET
import retrofit2.http.Header

data class ProfileResponse(
    val email: String,
    val nickname: String,
    val description : String,
    val profileImage: String
)

interface GetProfileInfoService {
    @GET("/auth/me")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String,
    ): ProfileResponse
}