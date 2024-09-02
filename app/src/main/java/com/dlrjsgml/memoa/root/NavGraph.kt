package com.dlrjsgml.memoa.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NavGroup.START) {
        composable(NavGroup.START){

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