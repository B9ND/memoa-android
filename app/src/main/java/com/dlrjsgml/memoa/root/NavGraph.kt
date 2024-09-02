package com.dlrjsgml.memoa.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dlrjsgml.memoa.feature.auth.start.StartScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NavGroup.START) {
        composable(NavGroup.START){
            StartScreen(navController)
        }
        composable(NavGroup.LOGIN){

        }
        composable(NavGroup.SIGNUP_EMAIL){

        }
        composable(NavGroup.SIGNUP_PASSWORD){

        }
        composable(NavGroup.SIGNUP_NICKNAME){

        }
        composable(NavGroup.SIGNUP_SCHOOL){

        }
        composable(NavGroup.SIGNUP_SCHOOL_NOT_FOUND){
            
        }
        
    }
}