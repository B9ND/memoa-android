package com.dlrjsgml.memoa

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MemoaTheme {
                val navHostController = rememberNavController()
                NavGraph(navController = navHostController)
            }
        }
    }
}
