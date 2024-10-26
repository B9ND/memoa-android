package com.dlrjsgml.memoa.feature.main.follower

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.FollowerButton
import com.dlrjsgml.memoa.ui.component.button.Segment
import com.dlrjsgml.memoa.ui.component.button.SegmentedButton
import com.dlrjsgml.memoa.ui.component.button.SegmentedButtonRow
import com.dlrjsgml.memoa.ui.component.items.FollowerList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FollowerScreen(
    userId: String,
    navController: NavHostController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Box(modifier = Modifier.padding(top = 32.dp, start = 20.dp)){
            BackButton {
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        SegmentedButtonRow(
            segments = persistentListOf(
                Segment(
                    selected = false,
                    onClick = { /* 팔로워 클릭 시 동작 */ },
                    text = "팔로워"
                ),
                Segment(
                    selected = false,
                    onClick = { /* 팔로잉 클릭 시 동작 */ },
                    text = "팔로잉"
                )
            )
        )
        LazyColumn {
            items(5){
                FollowerList()
            }
        }



    }
}


@Preview
@Composable
private fun fadjkdajk(){
//    FollowerScreen(userId = "1")
}