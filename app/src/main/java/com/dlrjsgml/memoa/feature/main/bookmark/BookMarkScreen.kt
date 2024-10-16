package com.dlrjsgml.memoa.feature.main.bookmark

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.backhandler.HomeBackOnPressed
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.ui.component.MemoaCheckBox
import com.dlrjsgml.memoa.ui.component.items.ArticleList

@Composable
fun BookMarkScreen(
    viewModel: BookMarkViewModel = viewModel(),
    navController: NavHostController
) {
    val selectTags = arrayListOf("국어", "영어", "수학", "사회", "과학", "기타")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        BackHandlers(navController = navController)
        Spacer(modifier = Modifier.height(30.dp))
        LazyRow(modifier = Modifier.padding(horizontal = 20.dp)) {
            items(selectTags.size) {
                MemoaCheckBox(
                    text = selectTags[it],
                    onClick = { viewModel.fillTags(selectTags[it]) } // Pass the single tag
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ArticleList()
    }
}

@Preview
@Composable
private fun fdjajfk() {
//    BookMarkScreen()
}