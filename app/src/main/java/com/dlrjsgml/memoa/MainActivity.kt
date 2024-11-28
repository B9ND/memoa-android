package com.dlrjsgml.memoa

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
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
import com.dlrjsgml.memoa.data.local.UserDatabase
import com.dlrjsgml.memoa.network.data.user.getUser.getAccToken
import com.dlrjsgml.memoa.network.data.user.getUser.getRefToken
import com.dlrjsgml.memoa.network.data.user.saveUser.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveUser.saveRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


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

fun clearAppData(context: Context) {
    val cache = context.cacheDir //캐시 폴더 호출
    val appDir = File(cache.parent) //App Data 삭제를 위해 캐시 폴더의 부모폴더까지 호출
    if (appDir.exists()) {
        val children = appDir.list()
        for (s in children) {
            //App Data 폴더의 리스트를 deleteDir 를 통해 하위 디렉토리 삭제
            deleteDir(File(appDir, s))
        }
    }
}

fun deleteDir(dir: File?): Boolean {
    if (dir != null && dir.isDirectory) {
        val children = dir.list()

        //파일 리스트를 반복문으로 호출
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }


    //디렉토리가 비어있거나 파일이므로 삭제 처리
    return dir!!.delete()
}
