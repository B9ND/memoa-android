package com.dlrjsgml.memoa.network.write.image

import okhttp3.MultipartBody
import okhttp3.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class UpLoadImgResponse(
    val url: String
)
interface UpLoadImgService {
    @Multipart
    @POST("/image/upload")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ) : UpLoadImgResponse
}
