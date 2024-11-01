package com.dlrjsgml.memoa.feature.main.follower

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.Segment
import com.dlrjsgml.memoa.ui.component.button.SegmentedButtonRow
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
        SegmentedButtonRow(
            segments = persistentListOf(
                Segment(
                    selected = !uiState.value.isFollowing,
                    onClick = { viewModel.changeToFollowers() },
                    text = "팔로워"
                ),
                Segment(
                    selected = uiState.value.isFollowing,
                    onClick = { viewModel.changeToFollowings() },
                    text = "팔로잉"
                )
            ),
            selectedSegment = if (uiState.value.isFollowing) {
                Segment(selected = true, onClick = {}, text = "팔로잉")
            } else {
                Segment(selected = true, onClick = {}, text = "팔로워")
            },
            onSegmentSelected = { segment ->
                if (segment.text == "팔로워") {
                    viewModel.changeToFollowers()
                } else {
                    viewModel.changeToFollowings()
                }
            }
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


@Preview
@Composable
private fun fadjkdajk() {
//    FollowerScreen(userId = "1")
}