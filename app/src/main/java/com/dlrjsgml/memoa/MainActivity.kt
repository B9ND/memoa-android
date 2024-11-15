package com.dlrjsgml.memoa

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme

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
            MemoaTheme {
                val navHostController = rememberNavController()
                NavGraph(navController = navHostController)
            }
        }
    }
}
