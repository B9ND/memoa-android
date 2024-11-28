package com.dlrjsgml.memoa.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.network.data.user.getUser.getAccToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor(
    private val networkUtil: NetworkUtil
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!networkUtil.isNetworkConnected()) {
                throw NoConnectivityException()
            }

            val request = chain.request()
            val context = MemoaApplication.getContext()
            val shouldSkipHeader = request.url.encodedPath.contains("/auth") && !request.url.encodedPath.contains("/auth/me") ||
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

            return chain.proceed(newRequest)
        } catch (e: NoConnectivityException) {
            throw e
        } catch (e: Exception) {
            throw NetworkException(e.message ?: "네트워크 오류가 발생했습니다.")
        }
    }
}

class NetworkUtil(private val context: Context) {
    fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val isActiveNetwork = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            isActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            isActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

class NoConnectivityException : IOException("인터넷 연결이 없습니다. 네트워크 상태를 확인해주세요.")
class NetworkException(message: String) : IOException(message)


