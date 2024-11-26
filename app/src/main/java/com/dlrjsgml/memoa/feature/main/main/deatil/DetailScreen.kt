package com.dlrjsgml.memoa.feature.main.main.deatil

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.safePopBackStack
import com.dlrjsgml.memoa.feature.main.main.MainViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.BookMarkButton
import com.dlrjsgml.memoa.ui.component.button.CommentButton
import com.dlrjsgml.memoa.ui.component.items.ArticleImage
import com.dlrjsgml.memoa.ui.component.items.CommentList
import com.dlrjsgml.memoa.ui.component.items.TagLists
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.boardContent
import kotlinx.collections.immutable.toImmutableList

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel(),
    bookMarkViewModel: MainViewModel = viewModel(),
    navController: NavHostController,
    boardNumber: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    val boards = uiState.content.split("✔")
    LaunchedEffect(Unit) {
        viewModel.getDetailInfo(boardNumber.toInt())
        Log.d("글만", "글 리스트는 : ${boards}");
    }
    Log.d("글만", "글 리스트는 : ${boards}");

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DetailInfoSideEffect.Failure -> Log.d("디테일", "성공");
                DetailInfoSideEffect.Success -> Log.d("디테일", "실패");
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 20.dp)
            ) {
                BackButton {
                    navController.safePopBackStack()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CommentList(
                name = uiState.author,
                date = uiState.createdAt,
                title = uiState.title,
                profile = uiState.authorProfileImage,
                onProfileClick = { navController.navigate("${NavGroup.USERPROFILE}?${uiState.author}") },
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        items(boards.size) { board ->
            val content = boards[board]
            val firstChar = content.firstOrNull()
            if (firstChar == '★') {
                val imageUrl = content.replace("★", "")
                Box(modifier = Modifier.padding(horizontal = 36.dp)) {
                    ArticleImage(
                        image = imageUrl,
                        onImageClick = { navController.navigate("${NavGroup.IMAGE_DETAIL}?$imageUrl") }
                    )
                }

                Log.d("디테일", "이미지 있음 ${content.replace("★", "")}");
            } else {
                Text(
                    modifier = Modifier.padding(horizontal = 36.dp),
                    text = content.toString(),
                    style = boardContent
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(horizontal = 36.dp)) {
                TagLists(uiState.tags.toImmutableList(), mini = true)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(horizontal = 36.dp)) {
                Row {
                    CommentButton(onClick = { navController.navigate("${NavGroup.COMMENT}/phone=ddddddd") })
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "todo",
                        color = Gray40,
                        style = boardContent.copy(fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    BookMarkButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        bookmarked = uiState.isBookmarked,
                        onClick = {bookMarkViewModel.bookmark(uiState.id)})
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "",
                        color = Gray40,
                        style = boardContent.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun DetailPreview() {
//    DetailScreen(boardNumber = 1)
//}