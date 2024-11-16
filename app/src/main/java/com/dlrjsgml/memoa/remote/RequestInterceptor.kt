package com.dlrjsgml.memoa.remote

import android.util.Log
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.network.data.user.getAccToken
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val context = MemoaApplication.getContext()
        val shouldSkipHeader = request.url.encodedPath.contains("/auth") ||
                request.url.encodedPath.contains("/school") ||
                request.url.encodedPath.contains("/auth/login")

        Log.d("데이터스토어 최종본", "intercept: ${getAccToken(context)}")
        val newRequest = if (shouldSkipHeader) {
            request.newBuilder().build()
        } else {
            request.newBuilder()
                .addHeader("Authorization", "Bearer ${getAccToken(context)}")
                .build()
        }
        println("Request URL: ${newRequest.url}")
        println("Headers: ${newRequest.headers}")

        val response = chain.proceed(newRequest)

        println("Response Code: ${response.code}")

        return response
    }
}

