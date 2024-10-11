package com.dlrjsgml.memoa.feature.main.main.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.feature.main.main.deatil.DetailViewModel
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.items.CommentList

@Composable
fun CommentScreen(
    viewModel: CommentViewModel = viewModel(),
    navController: NavHostController,
    boardNumber: String,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 20.dp)
                .padding(horizontal = 20.dp)

        ) {
            BackButton {
                navController.popBackStack()
            }
        }
        LazyColumn {
            items(10) {
                CommentList()
            }
        }
    }

}