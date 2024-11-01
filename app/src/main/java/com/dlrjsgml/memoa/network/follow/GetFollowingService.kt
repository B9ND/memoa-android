package com.dlrjsgml.memoa.network.follow

import com.dlrjsgml.memoa.network.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetFollowingService {
    @GET("/follow/followings")
    suspend fun getFollowingList(
        @Header("Authorization") token: String,
        @Query("user") user: String,
    ): List<ProfileResponse>
}

interface GetFollowersService {
    @GET("/follow/followers")
    suspend fun getFollowersList(
        @Header("Authorization") token: String,
        @Query("user") user: String,
    ): List<ProfileResponse>
}