package com.dlrjsgml.memoa.remote

import android.util.Log
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.network.data.user.getUser.getAccToken
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
//    private val networkUtil: NetworkUtil
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!networkUtil.isNetworkConnected()) {
//            Log.d("인터넷오류", "intercept:")
//            throw IOException("Network connection is lost")
//        }
        val request = chain.request()
        val context = MemoaApplication.getContext()
        val shouldSkipHeader = request.url.encodedPath.contains("/auth") && !request.url.encodedPath.contains("/auth/me")||
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

//class NetworkUtil(private val context: Context) {
//    @SuppressLint("MissingPermission")
//    fun isNetworkConnected(): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val activeNetwork = connectivityManager.activeNetwork ?: return false
//            val isActiveNetwork = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//            return when {
//                isActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                isActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                else -> false
//            }
//        } else {
//            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
//            return networkInfo.isConnected
//        }
//    }
//}

