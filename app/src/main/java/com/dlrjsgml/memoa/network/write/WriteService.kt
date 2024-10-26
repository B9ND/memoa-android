package com.dlrjsgml.memoa.network.write

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class WriteDTO(
    val title: String,
    val content: String,
    val tags: List<String>,
    val images: List<String>,
    val isReleased: Boolean = true
)

interface WriteService {
    @POST("/post")
    suspend fun postWrite(
        @Header("Authorization") token: String,
        @Body writeDTO: WriteDTO
    )
}
