package com.dlrjsgml.memoa.feature.main.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.MemoaCheckBox
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.theme.caption1

@Composable
fun BookMarkScreen(
    viewModel: BookMarkViewModel = viewModel(),
    navController: NavHostController,
) {
    val selectTags = arrayListOf("국어", "영어", "수학", "사회", "과학", "기타")
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBookMarks()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        BackHandlers(navController = navController)
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(58.dp))
            }
            if (uiState.bookMarks.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "북마크가 없습니다",
                        style = caption1
                    )
                }
            } else {
                items(uiState.bookMarks.size) {
                    val bookMark = uiState.bookMarks[it]
                    ArticleList(
                        name = bookMark.nickname,
                        profile = bookMark.profileImage,
                        date = bookMark.createdAt,
                        title = bookMark.title,
                        onArticleClick = { navController.navigate("${NavGroup.DETAIL}?${bookMark.postId}") },
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun fdjajfk() {
//    BookMarkScreen()
}