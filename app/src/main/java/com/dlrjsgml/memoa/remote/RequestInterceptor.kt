package com.dlrjsgml.memoa.remote

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 특정 경로나 조건에 따라 헤더를 추가하지 않음
        if (request.url.encodedPath.contains("/auth")) {
            when(request.url.encodedPath){
                "/auth/me" -> {}
                "/auth/signup" -> {}
                "/school/search" -> {}
                else -> return chain.proceed(request)
            }
        }

        // 그 외의 요청에는 토큰을 추가
        val newRequest = request.newBuilder()
            .addHeader("Authorization", TemporaryToken.AccessToken)
            .build()

        return chain.proceed(newRequest)
    }
}