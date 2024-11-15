package com.dlrjsgml.memoa.remote

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.user.getAccToken
import com.dlrjsgml.memoa.network.data.user.getRefToken
import com.dlrjsgml.memoa.network.data.user.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val statusCode = response.code // `code` 값을 `val`로 저장
        val context = MemoaApplication.getContext()


        when (statusCode) {
            400 -> {
            }
            401 -> {
                val newTokenResponse = runBlocking {
                    getRefToken(context)?.let { refToken ->
                        val tokenData = AccTokenRequest(refToken)
                        try {
                            val tokenResponse = RetrofitClient.tokenService.token(tokenData)
                            saveAccToken(context, tokenResponse.access)
                            saveRefToken(context, tokenResponse.refresh)
                        } catch (e: Exception) {
                            Log.e("토큰 갱신 오류", e.message ?: "토큰 갱신 실패")
                            null
                        }
                    }
                }

                newTokenResponse?.let {
                    chain.proceed(
                        request.newBuilder()
                            .header("Authorization", "Bearer $newTokenResponse")
                            .build()
                    )
                } ?: response
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
