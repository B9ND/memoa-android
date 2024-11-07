package com.dlrjsgml.memoa.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val statusCode = response.code // `code` 값을 `val`로 저장

        when (statusCode) {
            400 -> {
                // todo Control Error for Bad Request
            }
            401 -> {
                // todo Control Error for Unauthorized
            }
            402 -> {
                // todo Control Error for Payment Required
            }
            403 -> {
                Log.d("인터셉터", "403 에러");
            }
        }
        return response
    }
}
