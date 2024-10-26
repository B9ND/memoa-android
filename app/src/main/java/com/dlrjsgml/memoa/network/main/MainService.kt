package com.dlrjsgml.memoa.network.main

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

data class ArticleResponse(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val tags: List<String>,
    val createdAt : String,
    val images : List<String>
)


data class ArticleDataRequest(
    val search: String?,
    val tags: List<String?>?,
    val page: Int?,
    val size: Int?,
)

interface GetMainService {
    @GET("/post")
    suspend fun getArticles(
        @Header("Authorization") token: String,
        @Query("search") search: String,
        @Query("tags") tags: List<String>,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : List<ArticleResponse>
}