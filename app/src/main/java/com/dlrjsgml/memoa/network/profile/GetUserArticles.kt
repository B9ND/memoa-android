package com.dlrjsgml.memoa.network.profile

import com.dlrjsgml.memoa.network.main.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserArticles {
    @GET("/post/user")
    suspend fun getUserArticles(
        @Query("author") author : String
    ): List<ArticleResponse>
}