package com.dlrjsgml.memoa.network.follow

import com.dlrjsgml.memoa.network.profile.ProfileResponse
import com.dlrjsgml.memoa.network.profile.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class FollowResponse(
    val email: String,
    val nickname: String,
    val profileImage: String,
    val isFollowed : Boolean
)

interface GetFollowingService {
    @GET("/follow/followings")
    suspend fun getFollowingList(
        @Query("nickname") user: String,
    ): List<FollowResponse>
}

interface GetFollowersService {
    @GET("/follow/followers")
    suspend fun getFollowersList(
        @Query("nickname") user: String,
    ): List<FollowResponse>
}

interface FollowService{
    @POST("/follow")
    suspend fun follow(
        @Query("nickname") nickname: String,
    )
}