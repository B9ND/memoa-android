package com.dlrjsgml.memoa.network.bookmark

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class BookMarkResponse(
    val nickname : String,
    val postId : Int,
    val title : String,
    val profileImage : String,
    val createdAt : String,

)

interface GetBookMarkService {
    @GET("/bookmark")
    suspend fun getBookMark(): List<BookMarkResponse>
}
interface PostBookMarkService {
    @POST("/bookmark")
    suspend fun postBookMark(
        @Query("post-id") id: Int
    )
}