package com.dlrjsgml.memoa.network.main.detail

import com.dlrjsgml.memoa.network.main.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {
    @GET("/post/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int,
    ): ArticleResponse
}