package com.dlrjsgml.memoa.network.profile

import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserProfileInfoService {
    @GET("/auth/user")
    suspend fun getUserProfileInfo(
        @Query("username") username : String
    ): ProfileResponse
}