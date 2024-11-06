package com.dlrjsgml.memoa.feature.main.follower

import DodamSegment
import DodamSegmentedButton
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.items.FollowerList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FollowerScreen(
    userId: String,
    followingChecker: String,
    navController: NavHostController,
    viewModel: FollowViewModel = viewModel(),
) {

    val uiState = viewModel.uiState.collectAsState()
    Log.d("팔팔", userId);
    var selectedItem by remember { mutableStateOf(if(followingChecker == "true") "팔로우" else "팔로잉") }
    LaunchedEffect(Unit) {
        if (followingChecker == "true") viewModel.changeToFollowers() else viewModel.changeToFollowings()
        viewModel.getFollow(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(modifier = Modifier.padding(top = 32.dp, start = 20.dp)) {
            BackButton {
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        DodamSegmentedButton(
            segments = persistentListOf(
                DodamSegment(
                    selected = selectedItem.isOut(),
                    onClick = { selectedItem = "팔로우"
                              viewModel.changeToFollowers()},
                    text = "팔로우",
                ),
                DodamSegment(
                    selected = !selectedItem.isOut(),
                    onClick = { selectedItem = "팔로잉"
                              viewModel.changeToFollowings()},
                    text = "팔로잉",
                ),
            ),
        )
        LazyColumn {
            if (uiState.value.isFollowing) {
                items(uiState.value.followings.size) {
                    FollowerList(
                        name = uiState.value.followings[it].nickname,
                        profile = uiState.value.followings[it].profileImage
                    )
                }
            } else {
                items(uiState.value.followers.size) {
                    FollowerList(
                        name = uiState.value.followers[it].nickname,
                        profile = uiState.value.followers[it].profileImage
                    )
                }
            }
        }
    }
}
private fun String.isOut() = this == "팔로우"

@Preview
@Composable
private fun fadjkdajk() {
    val navController = rememberNavController()
    FollowerScreen(
        userId = "이건희",
        followingChecker = "true",
        navController = navController,
        viewModel = viewModel()
    )
}