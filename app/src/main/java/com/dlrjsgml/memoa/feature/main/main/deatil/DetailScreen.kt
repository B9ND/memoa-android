package com.dlrjsgml.memoa.feature.main.main.deatil

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.feature.main.main.ArticlesSideEffect
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.BookMarkButton
import com.dlrjsgml.memoa.ui.component.button.CommentButton
import com.dlrjsgml.memoa.ui.component.items.ArticleImage
import com.dlrjsgml.memoa.ui.component.items.CommentList
import com.dlrjsgml.memoa.ui.component.items.TagLists
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel(),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            BackButton {
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CommentList(
            name = uiState.author,
            date = uiState.createdAt,
            title = uiState.title,
            profile = uiState.authorProfileImage,
            onProfileClick = {navController.navigate("${NavGroup.USERPROFILE}?${uiState.author}")},

        )
        Spacer(modifier = Modifier.height(30.dp))


        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Column {
                Log.d("디테일", "자르기 : ${uiState.content}");
                LazyColumn {
                    items(boards.size) { board ->
                        val content = boards[board]
                        val firstChar = content.firstOrNull()
                        if (firstChar == '★') {
                            val imageUrl = content.replace("★", "")
                            ArticleImage(
                                image = imageUrl,
                                onImageClick = { navController.navigate("${NavGroup.IMAGEDETAIL}?$imageUrl") }
//                                navController = navController
                            )
                            Log.d("디테일", "이미지 있음 ${content.toString().replace("★", "")}");
                        } else {
                            Text(
                                text = content.toString(),
                                style = boardContent
                            )
                        }
                    }
//                }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TagLists(uiState.tags.toImmutableList(), mini = true)
            Spacer(modifier = Modifier.height(5.dp))
            Row {
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
                        onClick = {})
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