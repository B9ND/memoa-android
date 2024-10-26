package com.dlrjsgml.memoa.backhandler

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.root.NavGroup

@Composable
fun BackHandlers(navController: NavHostController) {
    BackHandler(enabled = true, onBack = {
        navController.popBackStack()
        navController.navigate(NavGroup.MAIN)
    })
}