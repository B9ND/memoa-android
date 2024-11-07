package com.dlrjsgml.memoa.feature.main.search


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.main.paging.FetchFlow
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.JJapList
import com.dlrjsgml.memoa.ui.component.items.SearchHistoryList
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import com.dlrjsgml.memoa.ui.theme.boardContent1
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.getData()
        viewModel.beforeSearch()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackHandlers(navController = navController)
        Spacer(modifier = Modifier.height(24.dp))
        SearchTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = uiState.search,
            onValueChange = viewModel::updateTitle,
            hint = "검색어를 입력하세요",
            onClick = {
                if (uiState.search.isNotEmpty()) {
                    viewModel.getSearchArticles()
                    viewModel.addData(uiState.search)
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                if (uiState.search.isNotEmpty()) {
                    viewModel.getSearchArticles()
                    viewModel.addData(uiState.search)
                }
            })
        )
        Spacer(modifier = Modifier.height(22.dp))
        Box {
            uiState.articles.let { state ->
                when (state) {
                    is FetchFlow.Failure -> Log.d("글보기", "오류");
                    is FetchFlow.Fetching -> {
                        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                            Text(
                                text = "최근 검색어",
                                style = boardContent1.copy(fontWeight = FontWeight.Normal)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            if (uiState.searchHistory.isNotEmpty()) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    uiState.searchHistory.reversed()
                                        .forEachIndexed { index, searchHistory ->
                                            if (index >= 4) return@forEachIndexed

                                            if (searchHistory.history.isNotEmpty()) {
                                                SearchHistoryList(
                                                    content = searchHistory.history,
                                                    onClick = {
                                                        viewModel.updateTitle(searchHistory.history)
                                                        viewModel.getSearchArticles()
                                                    }
                                                )
                                            }
                                        }
                                }
                            }
                        }
                    }

                    is FetchFlow.Success -> {
                        Log.d("백백", "성공~");
                        BackHandler {
                            viewModel.startFetching()
                            viewModel.getData()
                            viewModel.beforeSearch()
                            Log.d("백백", "ㅇㅇ : ");
                        }
                        val articlesItems = state.data.collectAsLazyPagingItems()
                        if (articlesItems.itemCount == 0) {
                            LazyColumn {
                                items(5){
                                    JJapList()
                                }
                            }
                        } else {

                            Log.d("백백", "레이지컬럼");
                            LazyColumn {
                                items(articlesItems.itemCount) {
                                    val article = articlesItems[it]
                                    if (article != null) {
                                        ArticleList(
                                            id = article.id,
                                            name = article.author,
                                            date = article.createdAt,
                                            title = article.title,
                                            image = article.images.toImmutableList(),
                                            profile = article.authorProfileImage,
                                            tag = article.tags.toImmutableList(),
                                            comment = 1,
                                            onBookmarkClick = {
//                                                viewModel.bookmark(article.id)
                                            },
                                            onCommentClick = {},
                                            onArticleClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") },
                                            onImageClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


suspend fun returnTrueAfterDelay(): Boolean {
    delay(1000) // 1000ms (1초) 지연
    return true
}
//
//
//@Preview
//@Composable
//private fun afdjkadjkfad(){
//    SearchScreen(
//        viewModel = viewModel(),
//        navController = NavHostController(n)
//    )
//}
//
