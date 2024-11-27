package com.dlrjsgml.memoa.root

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.safePopBackStack
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
import com.dlrjsgml.memoa.feature.main.main.detail.DetailScreen
import com.dlrjsgml.memoa.feature.main.profile.my.ProfileScreen
import com.dlrjsgml.memoa.feature.main.profile.my.setting.SettingScreen
import com.dlrjsgml.memoa.feature.main.profile.user.UserProfileScreen
import com.dlrjsgml.memoa.feature.main.search.SearchScreen
import com.dlrjsgml.memoa.feature.main.write.WriteScreen
import com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose.SchoolChooseScreenViewModel
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionScreen
import com.dlrjsgml.memoa.feature.main.profile.my.setting.name.NameSettingScreen
import com.dlrjsgml.memoa.feature.main.search.before.BeforeSearchScreen
import com.dlrjsgml.memoa.feature.main.search.ing.SearchingScreen
import com.dlrjsgml.memoa.network.data.user.getUser.getRefToken
import com.dlrjsgml.memoa.network.data.user.getUser.getUserProfile
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
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
        NavGroup.MAIN,
        NavGroup.DETAIL,
        "${NavGroup.SEARCH}?{search}",
        NavGroup.BEFORE_SEARCH,
        NavGroup.BOOKMARK,
        NavGroup.PROFILE,
        NavGroup.FOLLOWER,
        "${NavGroup.USERPROFILE}?{phone}",
        "${NavGroup.DETAIL}?{phone}",
        "userprofile?{phone}", "${NavGroup.USERPROFILE}?{phone}",
        "${NavGroup.FOLLOWER}?{phone}?{checker}"

    )

    val backstackEntry by navController.currentBackStackEntryAsState()
    val selectRoute = backstackEntry?.destination?.route
    Log.d("현재스택", " : ")
    Log.d("현재경로", "안녕 : $selectRoute")

    val isShowNavBar = selectRoute in showNavBarList
    Log.d("현재경로", "ggg : $isShowNavBar")

//    val navOptions = navOptions {
//        // 현재 화면 유지
//        launchSingleTop = true
//        restoreState = true
//    }
    val navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isShowNavBar,
                    enter = slideInVertically(
                        initialOffsetY = { it }, // 시작 위치: 컴포넌트의 높이만큼 아래에서 시작
                        animationSpec = tween(durationMillis = 200) // 300ms 애니메이션
                    ),
                    exit = ExitTransition.None

                ) {
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
                                    .clickable(
                                        indication = rememberBounceIndication(
                                            scale = 0.95f,
                                            showBackground = true,
                                            radius = RoundedCornerShape(8.dp)
                                        ),
                                        interactionSource = remember { MutableInteractionSource() },
                                        enabled = true,
                                        onClick = {
                                            navController.safePopBackStack()
                                            navController.navigate(NavGroup.MAIN) {
                                                popUpTo(NavGroup.MAIN) {
                                                    inclusive = false
                                                } // 이미 존재하면 백스택 유지
                                                launchSingleTop =
                                                    true                      // 동일 경로 중복 방지
                                                restoreState = true
                                            }
                                        }
                                    ),
                                resId = R.drawable.ic_home,
                                isSelected = selectRoute == NavGroup.MAIN || NavGroup.DETAIL in selectRoute.toString(),
                                text = "메인"
                            )
                            BottomNavItem(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(
                                        indication = rememberBounceIndication(
                                            scale = 0.95f,
                                            showBackground = true,
                                            radius = RoundedCornerShape(8.dp)
                                        ),
                                        interactionSource = remember { MutableInteractionSource() },
                                        enabled = true,
                                        onClick = {
                                            navController.safePopBackStack()
                                            navController.navigate(NavGroup.BEFORE_SEARCH) {
                                                popUpTo(NavGroup.BEFORE_SEARCH) {
                                                    inclusive = false
                                                } // 이미 존재하면 백스택 유지
                                                launchSingleTop =
                                                    true                      // 동일 경로 중복 방지
                                                restoreState = true
                                            }
                                        }
                                    ),
                                resId = R.drawable.ic_search,
                                isSelected = selectRoute == "${NavGroup.SEARCH}?{search}" || NavGroup.BEFORE_SEARCH in selectRoute.toString(),
                                text = "검색"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BottomNavItem(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(
                                        indication = rememberBounceIndication(
                                            scale = 0.95f,
                                            showBackground = true,
                                            radius = RoundedCornerShape(8.dp)
                                        ),
                                        interactionSource = remember { MutableInteractionSource() },
                                        enabled = true,
                                        onClick = {
                                            navController.safePopBackStack()
                                            navController.navigate(NavGroup.BOOKMARK) {
                                                popUpTo(NavGroup.BOOKMARK) {
                                                    inclusive = false
                                                } // 이미 존재하면 백스택 유지
                                                launchSingleTop =
                                                    true                      // 동일 경로 중복 방지
                                                restoreState = true
                                            }
                                        }
                                    ),
                                resId = R.drawable.ic_bookmark,
                                isSelected = selectRoute == NavGroup.BOOKMARK,
                                text = "북마크"
                            )
                            BottomNavItem(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable(
                                        indication = rememberBounceIndication(
                                            scale = 0.95f,
                                            showBackground = true,
                                            radius = RoundedCornerShape(8.dp)
                                        ),
                                        interactionSource = remember { MutableInteractionSource() },
                                        enabled = true,
                                        onClick = {
                                            navController.safePopBackStack()
                                            navController.navigate(NavGroup.PROFILE) {
                                                popUpTo(NavGroup.PROFILE) {
                                                    inclusive = false
                                                } // 이미 존재하면 백스택 유지
                                                launchSingleTop =
                                                    true                      // 동일 경로 중복 방지
                                                restoreState = true
                                            }
                                        }
                                    ),
                                resId = R.drawable.ic_avatar,
                                isSelected = selectRoute == NavGroup.PROFILE || NavGroup.USERPROFILE in selectRoute.toString(),
                                text = "프로필"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = (-14).dp)
                                .noRippleClickable {
                                    navController.navigate(
                                        NavGroup.WRITE
                                    ) {
                                        popUpTo(NavGroup.WRITE) {
                                            inclusive = false
                                        } // 이미 존재하면 백스택 유지
                                        launchSingleTop = true                      // 동일 경로 중복 방지
                                        restoreState = true
                                    }
                                }
                        ) {
                            BottomCircleTwo(
                                isSelected = selectRoute == NavGroup.WRITE,
                            )
                        }
                    }
                }
            }) { it ->
            Log.d("", "NavGraph: ")
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = NavGroup.MAIN,
                enterTransition = {
                    // you can change whatever you want transition
                    EnterTransition.None
                },
                exitTransition = {
                    // you can change whatever you want transition
                    ExitTransition.None
                },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
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
                    exitTransition = { null },
                    popExitTransition = { null },
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
                composable(
                    NavGroup.WRITE,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        WriteScreen(navController = navController)
                    }
                }
                composable(NavGroup.BOOKMARK) {
                    BookMarkScreen(navController = navController)
                }
                composable(
                    route = "${NavGroup.IMAGE_DETAIL}?{phone}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    ),
                ) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    ImageDetailScreen(
                        navController = navController,
                        imgUrl = phoneNum
                    )
                }
                composable(
                    NavGroup.PROFILE,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { ExitTransition.None },

                    ) {
                    ProfileScreen(navController)
                }
                composable(
                    route = "${NavGroup.USERPROFILE}?{phone}",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType }
                    ),
                ) {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    if (phoneNum == getUserProfile(MemoaApplication.getContext()).nickname) {
                        navController.navigate(NavGroup.PROFILE)
                    } else {
                        UserProfileScreen(
                            navController = navController,
                            userName = phoneNum
                        )
                    }

                }
                composable(route = "${NavGroup.FOLLOWER}?{phone}?{checker}",
                    arguments = listOf(
                        navArgument("phone") { NavType.StringType },
                        navArgument("checker") { NavType.StringType }
                    ),
                    enterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(100)
                        )
                    }, exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End, tween(100)
                        )
                    }, popEnterTransition = {
                        return@composable EnterTransition.None
                    })
                {
                    val phoneNum = it.arguments?.getString("phone") ?: ""
                    val checkers = it.arguments?.getString("checker") ?: "false"
                    FollowerScreen(
                        userId = phoneNum, followingChecker = checkers,
                        navController = navController
                    )
                }
                composable(
                    route = NavGroup.SETTING,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                ) {
                    SettingScreen(navController)
                }
                composable(
                    NavGroup.NAME_SETTING,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                ) {
                    NameSettingScreen(navController)
                }
                composable(
                    NavGroup.DESCRIPTION_SETTING,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
                ) {
                    DescriptionScreen(navController)
                }
            }

        }

    }
}

private fun getStartDestination(isLogined: Boolean) =
    if (isLogined) NavGroup.MAIN else NavGroup.START


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SeeNavGraph() {
    rememberNavController()
}
