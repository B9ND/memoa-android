package com.dlrjsgml.memoa.remote

import android.util.Log
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.network.data.user.clearToken
import com.dlrjsgml.memoa.network.data.user.getUser.getAccToken
import com.dlrjsgml.memoa.network.data.user.getUser.getRefToken
import com.dlrjsgml.memoa.network.data.user.saveUser.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveUser.saveRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()
            val response = chain.proceed(request)
            val statusCode = response.code
            val context = MemoaApplication.getContext()

            when (statusCode) {
                400 -> {
                    Log.e("API 오류", "잘못된 요청입니다.")
                }
                401, 402 -> {
                    val newTokenResponse = runBlocking {
                        try {
                            clearToken(MemoaApplication.getContext())
                            getRefToken(context)?.let { refToken ->
                                val tokenData = AccTokenRequest(refToken)
                                Log.d("토큰 갱신", "intercept: $tokenData")
                                val tokenResponse = RetrofitClient.tokenService.token(tokenData)
                                Log.d("토큰 갱신", "intercept1: $tokenResponse")
                                saveAccToken(context, tokenResponse.access)
                                saveRefToken(context, tokenResponse.refresh)
                                tokenResponse.access
                            }
                        } catch (e: Exception) {
                            Log.e("토큰 갱신 오류", e.message ?: "토큰 갱신 실패")
                            null
                        }
                    }

                    return newTokenResponse?.let {
                        chain.proceed(
                            request.newBuilder()
                                .header("Authorization", "Bearer $it")
                                .build()
                        )
                    } ?: response
                }
                403 -> {
                    Log.e("인터셉터", "권한이 없습니다.")
                }
            }
            return response

        } catch (e: NoConnectivityException) {
            throw e
        } catch (e: Exception) {
            throw NetworkException(e.message ?: "네트워크 오류가 발생했습니다.")
        }
    }
}