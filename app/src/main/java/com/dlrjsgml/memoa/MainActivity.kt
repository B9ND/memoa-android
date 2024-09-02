package com.dlrjsgml.memoa

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.feature.TestScreen
import com.dlrjsgml.memoa.root.NavGraph
import com.dlrjsgml.memoa.ui.theme.MemoaTheme
import com.dlrjsgml.memoa.ui.component.MemoaButton

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoaTheme {
                val navHostController = rememberNavController()
                NavGraph(navController = navHostController)
            }
        }
    }
}
