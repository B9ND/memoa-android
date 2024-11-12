package com.dlrjsgml.memoa.network.profile

import retrofit2.http.GET
import retrofit2.http.Header

data class UserInfo(
    val name : String = "",
    val grade : Int = -1,
    val school : String = "",
    val subjects : List<String> = listOf(),
)
data class ProfileResponse(
    val email: String,
    val nickname: String,
    val description : String,
    val profileImage: String,
    val department : UserInfo,
    val followed : Boolean
)

interface GetProfileInfoService {
    @GET("/auth/me")
    suspend fun getProfileInfo(
    ): ProfileResponse
}