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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.dlrjsgml.memoa.feature.main.profile.my.MyProfileState
import com.dlrjsgml.memoa.network.data.user.getAccToken
import com.dlrjsgml.memoa.network.data.user.getRefToken
import com.dlrjsgml.memoa.network.data.user.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.10)
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
            val coroutineScope = rememberCoroutineScope()
            var isLogin: Boolean? by remember { mutableStateOf(null) }
            Log.d("token쪽", "onCreate: ${getAccToken(MemoaApplication.getContext())}")
            Log.d("token쪽", "onCreate: ${getRefToken(MemoaApplication.getContext())}")

            MemoaTheme {
                val navHostController = rememberNavController()
//                isLogin?.let { NavGraph(isLogined = it, navController = navHostController) }
                NavGraph(navController = navHostController)
            }
        }
        Log.d("리프레쉬", "onCreate: ${getAccToken(context = MemoaApplication.getContext())}")
    }
}

//private suspend fun isLogin(context: Context): Boolean {
//    Log.d("LOGIN", "get Start")
//    val refToken = getRefToken(context)
//    Log.d("LOGIN", "get result: $refToken")
//    if (refToken != null) {
//        try {
//            val response = accToken(context)
//            return response == "success"
//        } catch (_: Exception) {
//            return false
//        }
//    }
//    return false
//}

//private suspend fun accToken(context: Context): String {
//    try {
//        val tokenData = getRefToken(context)?.let { AccTokenRequest(it) }
//        Log.d("스타트뷰모델", "accToken: }")
//        val response = tokenData?.let { RetrofitClient.tokenService.token(it) }
//        Log.d("스타트뷰모델", "accToken: 여기서 안됨")
//        if (response != null) {
//            saveAccToken(context, response.access)
//            saveRefToken(context, response.refresh)
//        }
//        return "success"
//    } catch (e: Exception) {
//        Log.d("스타트뷰모델", "error massage: $e")
//        return "fail"
//    }
//}
