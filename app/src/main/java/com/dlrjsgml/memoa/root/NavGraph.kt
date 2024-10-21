package com.dlrjsgml.memoa.root

import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.feature.SignUp.email.EmailScreen
import com.dlrjsgml.memoa.feature.login.LoginScreen
import com.dlrjsgml.memoa.feature.auth.start.StartScreen
import com.dlrjsgml.memoa.feature.main.bookmark.BookMarkScreen
import com.dlrjsgml.memoa.feature.main.follower.FollowerScreen
import com.dlrjsgml.memoa.feature.main.image.ImageDetailScreen
import com.dlrjsgml.memoa.feature.main.main.MainScreen
import com.dlrjsgml.memoa.feature.main.main.comment.CommentScreen
import com.dlrjsgml.memoa.feature.main.main.deatil.DetailScreen
import com.dlrjsgml.memoa.feature.main.profile.ProfileScreen
import com.dlrjsgml.memoa.feature.main.profile.setting.SettingScreen
import com.dlrjsgml.memoa.feature.main.search.SearchScreen
import com.dlrjsgml.memoa.feature.main.write.WriteScreen
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow
import com.dlrjsgml.memoa.ui.component.items.BottomCircleTwo
import com.dlrjsgml.memoa.ui.component.items.BottomNavItem
import com.dlrjsgml.memoa.ui.theme.Black20


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val showNavBarList = arrayListOf(
        NavGroup.START,
        NavGroup.LOGIN,
        NavGroup.SIGNUP_EMAIL,
        NavGroup.SIGNUP_PASSWORD,
        NavGroup.SIGNUP_NICKNAME,
        NavGroup.SIGNUP_SCHOOL,
        NavGroup.SIGNUP_SCHOOL_NOT_FOUND,
        NavGroup.WRITE,
        NavGroup.IMAGEDETAIL
    )
    val backstackEntry by navController.currentBackStackEntryAsState()
    val selectRoute = backstackEntry?.destination?.route

    var isShowNavBar = selectRoute !in showNavBarList
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(bottomBar = {
            if (isShowNavBar) {
                Box(
                    modifier = Modifier.drawColoredShadow(Black20)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                White,
                            )
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 15.dp, top = 4.dp)
                    ) {
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.MAIN)
                                }),
                            resId = R.drawable.ic_home,
                            isSelected = selectRoute == NavGroup.MAIN || NavGroup.DETAIL in selectRoute.toString() ,
                            text = "메인"
                        )
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.SEARCH)
                                }),
                            resId = R.drawable.ic_search,
                            isSelected = selectRoute == NavGroup.SEARCH,
                            text = "검색"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.BOOKMARK)
                                }),
                            resId = R.drawable.ic_bookmark,
                            isSelected = selectRoute == NavGroup.BOOKMARK,
                            text = "북마크"
                        )
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.PROFILE)
                                }),
                            resId = R.drawable.ic_avatar,
                            isSelected = selectRoute == NavGroup.PROFILE || NavGroup.SETTING in selectRoute.toString(),
                            text = "프로필"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = (-28).dp)
                            .noRippleClickable(onClick = {
                                navController.navigate(NavGroup.WRITE)
                            })
                    ) {
                        BottomCircleTwo(
                            isSelected = selectRoute == NavGroup.WRITE,
                        )
                    }
                }
            }
        }) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = NavGroup.START
            ) {
                composable(NavGroup.START) {
                    StartScreen(navController = navController)
                }
                composable(NavGroup.LOGIN) {
                    LoginScreen(navController = navController)
                }
                composable(NavGroup.SIGNUP_EMAIL) {
                    EmailScreen(navController = navController)
                }
                composable(NavGroup.SIGNUP_PASSWORD) {

                }
                composable(NavGroup.SIGNUP_NICKNAME) {

                }
                composable(NavGroup.SIGNUP_SCHOOL) {

                }
                composable(NavGroup.SIGNUP_SCHOOL_NOT_FOUND) {

                }
                composable(NavGroup.MAIN) {
                    MainScreen(navController)
                }
                composable(route = "${NavGroup.DETAIL}/phone={phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )){

                    val phoneNum =  it.arguments?.getString("phone")?: ""
                    DetailScreen(
                        navController = navController,
                        boardNumber = phoneNum
                    )
                }
                composable(route = "${NavGroup.COMMENT}/phone={phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )){
                    val phoneNum =  it.arguments?.getString("phone")?: ""
                    CommentScreen(
                        navController = navController,
                        boardNumber = phoneNum
                    )
                }
                composable(NavGroup.SEARCH) {
                    SearchScreen(navController = navController)
                }
                composable(NavGroup.WRITE) {
                    WriteScreen(navController = navController)
                }
                composable(NavGroup.BOOKMARK) {
                    BookMarkScreen(navController = navController)
                }
                composable(route = "${NavGroup.IMAGEDETAIL}?{phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )){
                    isShowNavBar = false
                    val phoneNum =  it.arguments?.getString("phone")?: ""
                    ImageDetailScreen(
                        navController = navController,
                        imgUrl = phoneNum
                    )
                }
                composable(NavGroup.PROFILE) {
                    ProfileScreen(navController)
                }
                composable(route = "${NavGroup.FOLLOWER}/phone={phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )){
                    val phoneNum =  it.arguments?.getString("phone")?: ""
                    FollowerScreen(
                        userId = phoneNum,
                        navController = navController
                    )
                }
                composable(NavGroup.SETTING) {
                    SettingScreen()
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SeeNavGrapj() {
    val navHostController = rememberNavController()
    NavGraph(navController = navHostController)
}