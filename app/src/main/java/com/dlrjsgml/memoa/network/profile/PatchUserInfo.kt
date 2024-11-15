package com.dlrjsgml.memoa.network.profile

import retrofit2.http.Body
import retrofit2.http.PATCH

data class ChangeUserInfoRequest(
    val nickname: String?,
    val description: String?,
    val profileImage: String?,
    val department: Int?,
    val password: String?,
    val pastPassword: String?,
)

interface PatchUserInfo {
    @PATCH("/auth/me")
    suspend fun changeUserInfo(
        @Body request: ChangeUserInfoRequest
    ): ProfileResponse
}