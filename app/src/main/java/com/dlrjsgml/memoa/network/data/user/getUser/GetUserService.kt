package com.dlrjsgml.memoa.network.data.user.getUser

import retrofit2.http.GET

interface GetUserService {
    @GET("/auth/me")
    suspend fun getUserService(): GetResponse
}
