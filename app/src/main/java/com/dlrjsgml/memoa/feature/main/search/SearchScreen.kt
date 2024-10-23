package com.dlrjsgml.memoa.feature.main.search


import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.main.paging.FetchFlow
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.JJapList
import com.dlrjsgml.memoa.ui.component.items.SearchHistoryList
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
        viewModel.beforeSearch()
    }
<<<<<<< HEAD

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackHandlers(navController = navController)
=======
    val searchText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("검색어")
        }
        append("를 입력하세요")
    }
    Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(scrollState)) {
//        BackHandlers(navController = navController)
>>>>>>> d5aeb34 (feat: singUpScreen)
        Spacer(modifier = Modifier.height(24.dp))
        SearchTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = uiState.search,
            onValueChange = viewModel::updateTitle,
            hint = searchText,
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
                        val articlesItems = state.data.collectAsLazyPagingItems()
                        if (articlesItems.itemCount == 0) {
                            Text("검색한 결과 없음", style = boardContent1)
                        } else {
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
                                            profile = "https://image.kmib.co.kr/online_image/2020/0920/611718110015025888_4.jpg",
                                            tag = article.tags.toImmutableList(),
                                            comment = 1,
                                            bookmarkClick = { },
                                            commentClick = {},
                                            navController = navController
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
