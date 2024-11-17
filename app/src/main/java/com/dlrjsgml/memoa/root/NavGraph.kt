package com.dlrjsgml.memoa.root

import android.os.Build
import android.util.Log
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
import com.dlrjsgml.memoa.feature.auth.start.login.LoginScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.email.EmailScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.name.NameScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.password.PasswordScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose.SchoolChooseScreen
import com.dlrjsgml.memoa.feature.auth.start.start.StartScreen
import com.dlrjsgml.memoa.feature.main.bookmark.BookMarkScreen
import com.dlrjsgml.memoa.feature.main.follower.FollowerScreen
import com.dlrjsgml.memoa.feature.main.image.ImageDetailScreen
import com.dlrjsgml.memoa.feature.main.main.MainScreen
import com.dlrjsgml.memoa.feature.main.main.comment.CommentScreen
import com.dlrjsgml.memoa.feature.main.main.deatil.DetailScreen
import com.dlrjsgml.memoa.feature.main.profile.my.ProfileScreen
import com.dlrjsgml.memoa.feature.main.profile.my.setting.SettingScreen
import com.dlrjsgml.memoa.feature.main.profile.user.UserProfileScreen
import com.dlrjsgml.memoa.feature.main.search.SearchScreen
// <<<<<<< feature/setting
import com.dlrjsgml.memoa.feature.main.write.WriteScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.password.PasswordScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose.SchoolChooseScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose.SchoolChooseScreenViewModel
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionScreen
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionState
import com.dlrjsgml.memoa.feature.main.profile.my.setting.name.NameSettingScreen
// =======
// >>>>>>> develop
import com.dlrjsgml.memoa.feature.main.search.before.BeforeSearchScreen
import com.dlrjsgml.memoa.feature.main.search.ing.SearchingScreen
import com.dlrjsgml.memoa.feature.main.write.WriteScreen
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow
import com.dlrjsgml.memoa.ui.component.items.BottomCircleTwo
import com.dlrjsgml.memoa.ui.component.items.BottomNavItem
import com.dlrjsgml.memoa.ui.theme.Black20


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    isLogined: Boolean,
    navController: NavHostController,
) {

    val showNavBarList = arrayListOf(
        NavGroup.MAIN,
        NavGroup.DETAIL,
        "${NavGroup.SEARCH}?{search}",
        NavGroup.BEFORE_SEARCH,
        NavGroup.BOOKMARK,
        NavGroup.PROFILE,
        NavGroup.FOLLOWER,
        "${NavGroup.USERPROFILE}?{phone}",
        "${NavGroup.DETAIL}?{phone}",
        "userprofile?{phone}", "${NavGroup.USERPROFILE}?{phone}"
    )

    val backstackEntry by navController.currentBackStackEntryAsState()
    val selectRoute = backstackEntry?.destination?.route
    Log.d("현재경로", "안녕 : $selectRoute")

    val isShowNavBar = selectRoute in showNavBarList
    Log.d("현재경로", "ggg : $isShowNavBar")

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
                    ) {
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.MAIN)
                                }),
                            resId = R.drawable.ic_home,
                            isSelected = selectRoute == NavGroup.MAIN || NavGroup.DETAIL in selectRoute.toString(),
                            text = "메인"
                        )
                        BottomNavItem(
                            modifier = Modifier
                                .weight(1f)
                                .noRippleClickable(onClick = {
                                    navController.popBackStack()
                                    navController.navigate(NavGroup.BEFORE_SEARCH)
                                }),
                            resId = R.drawable.ic_search,
                            isSelected = selectRoute == "${NavGroup.SEARCH}?{search}" || NavGroup.BEFORE_SEARCH in selectRoute.toString(),
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
                            isSelected = selectRoute == NavGroup.PROFILE || NavGroup.USERPROFILE in selectRoute.toString(),
                            text = "프로필"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = (-14).dp)
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
        }) { it ->
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = if (isLogined) NavGroup.MAIN else NavGroup.START
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
                composable("${NavGroup.SIGNUP_PASSWORD}?{email}") {
                    val email = it.arguments?.getString("email") ?: ""
                    PasswordScreen(navController = navController, email = email)
                }
                composable("${NavGroup.SIGNUP_NICKNAME}?{email}?{password}") {
                    val email = it.arguments?.getString("email") ?: ""
                    val password = it.arguments?.getString("password") ?: ""
                    NameScreen(navController = navController, email = email, password = password)
                }
                composable("${NavGroup.SIGNUP_SCHOOL}?{email}?{password}?{nickname}") {
                    val email = it.arguments?.getString("email") ?: ""
                    val password = it.arguments?.getString("password") ?: ""
                    val nickname = it.arguments?.getString("nickname") ?: ""
                    SchoolChooseScreen(
                        navController = navController,
                        viewModel = SchoolChooseScreenViewModel(),
                        email = email,
                        password = password,
                        nickname = nickname
                    )
                }
                composable(NavGroup.SIGNUP_SCHOOL_NOT_FOUND) {

                }
                composable(NavGroup.MAIN) {
                    MainScreen(navController = navController)
                }
                composable(route = "${NavGroup.DETAIL}?{phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )) {

                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    DetailScreen(
                        navController = navController,
                        boardNumber = phoneNum
                    )
                }
                composable(route = "${NavGroup.COMMENT}/phone={phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    CommentScreen(
                        navController = navController,
                        boardNumber = phoneNum
                    )
                }
                composable(route = "${NavGroup.SEARCH}?{search}",
                    arguments = listOf(
                        navArgument("search") { NavType.StringType }
                    )) {
                    val search = it.arguments?.getString("search") ?: ""
                    SearchScreen(
                        navController = navController,
                        search = search
                    )
                }
                composable(NavGroup.BEFORE_SEARCH) {
                    BeforeSearchScreen(navController = navController)
                }
                composable(NavGroup.SEARCHING) {
                    SearchingScreen(navController = navController)
                }
                composable(NavGroup.WRITE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        WriteScreen(navController = navController)
                    }
                }
                composable(NavGroup.BOOKMARK) {
                    BookMarkScreen(navController = navController)
                }
                composable(route = "${NavGroup.IMAGE_DETAIL}?{phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    ImageDetailScreen(
                        navController = navController,
                        imgUrl = phoneNum
                    )
                }
                composable(NavGroup.PROFILE) {
                    ProfileScreen(navController)
                }
                composable(route = "${NavGroup.USERPROFILE}?{phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    )) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    UserProfileScreen(
                        navController = navController,
                        userName = phoneNum
                    )
                }

                composable(route = "${NavGroup.FOLLOWER}?{phone}?{checker}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType },
                        navArgument("checker") { NavType.StringType }
                    )) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    val checkers = it.arguments?.getString("checker") ?: "false"
                    FollowerScreen(
                        userId = phoneNum, followingChecker = checkers,
                        navController = navController
                    )
                }
                composable(NavGroup.SETTING) {
                    SettingScreen(navController)
                }
                composable(NavGroup.NAME_SETTING) {
                    NameSettingScreen(navController)
                }
                composable(NavGroup.DESCRIPTION_SETTING) {
                    DescriptionScreen(navController)
                }
            }

        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SeeNavGraph() {
    rememberNavController()
}