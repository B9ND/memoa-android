package com.dlrjsgml.memoa.network.follow

import com.dlrjsgml.memoa.network.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GetFollowingService {
    @GET("/follow/followings")
    suspend fun getFollowingList(
        @Query("nickname") user: String,
    ): List<ProfileResponse>
}

interface GetFollowersService {
    @GET("/follow/followers")
    suspend fun getFollowersList(
        @Query("nickname") user: String,
    ): List<ProfileResponse>
}

interface FollowService{
    @POST("/follow")
    suspend fun follow(
        @Query("nickname") nickname: String,
    )
}