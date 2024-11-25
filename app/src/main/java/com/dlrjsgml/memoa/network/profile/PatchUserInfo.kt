package com.dlrjsgml.memoa.network.profile

import retrofit2.http.Body
import retrofit2.http.PATCH

data class ChangeUserNameRequest(
    val nickname: String?
)
data class ChangeUserDescriptionRequest(
    val description: String?
)
data class ChangeProfileImageRequest(
    val profileImage: String?
)
interface PatchUserInfo {
    @PATCH("/auth/me")
    suspend fun changeUserName(
        @Body request: ChangeUserNameRequest
    ): ProfileResponse
    @PATCH("/auth/me")
    suspend fun changeUserDescription(
        @Body request: ChangeUserDescriptionRequest
    ): ProfileResponse
    @PATCH("/auth/me")
    suspend fun changeProfileImage(
        @Body request: ChangeProfileImageRequest
    ): ProfileResponse

}