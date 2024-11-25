package com.dlrjsgml.memoa.backhandler

import androidx.navigation.NavHostController

fun NavHostController.safePopBackStack() {
    if (previousBackStackEntry != null) {
        popBackStack()
    }
}