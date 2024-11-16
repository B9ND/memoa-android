package com.dlrjsgml.memoa

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.dlrjsgml.memoa.network.data.user.getRefToken
import com.dlrjsgml.memoa.network.data.user.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.10) // 메모리 캐시 최대 크기 (앱 메모리의 10%)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_cache"))
                    .maxSizeBytes(10L * 300L * 300L)
                    .build()
            }
            .build()

        Coil.setImageLoader(imageLoader)


        setContent {
            var isLogin: Boolean? by remember { mutableStateOf(null) }

            LaunchedEffect(Unit) {
                launch {
                    isLogin = isLogin(this@MainActivity)
                }
            }
            MemoaTheme {
                val navHostController = rememberNavController()
                isLogin?.let { NavGraph(isLogined = it, navController = navHostController) }
            }
        }
    }
}

private suspend fun isLogin(context: Context): Boolean {
    val refToken = getRefToken(context)
    Log.d("리프", "isLogin: $refToken")
    if (refToken != null) {
        try {
            val response = accToken(context)
            Log.d("로그인확인", "loginChecker: $response")
            return response == "success"
        } catch (_: Exception) {
            return false
        }
    }
    return false
}

private suspend fun accToken(context: Context): String {
    try {
        val tokenData = getRefToken(context)?.let { AccTokenRequest(it) }
        val response = tokenData?.let { RetrofitClient.tokenService.token(it) }
        if (response != null) {
            saveAccToken(context, response.access)
            saveRefToken(context, response.refresh)
        }
        return "success"
    } catch (e: Exception) {
        Log.d("스타트뷰모델", "error massage: $e")
        return "fail"
    }
}
