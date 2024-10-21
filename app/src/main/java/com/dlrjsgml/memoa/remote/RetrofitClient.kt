package com.dlrjsgml.memoa.remote

import com.dlrjsgml.memoa.network.write.WriteService
import com.dlrjsgml.memoa.network.write.image.UpLoadImgService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://13.125.84.202"
    var gson= GsonBuilder().setLenient().create()
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC // 요청 메서드 및 URL만 로그에 남기기
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create() )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    val writeService : WriteService by lazy { instance.create(WriteService::class.java) }
    val upLoadImgService : UpLoadImgService by lazy { instance.create(UpLoadImgService::class.java) }

}