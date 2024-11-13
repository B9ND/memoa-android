package com.dlrjsgml.memoa.feature.main.search


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.main.paging.FetchFlow
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.JJapList
import com.dlrjsgml.memoa.ui.component.items.SearchHistoryList
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption2
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    navController: NavHostController,
    search: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.getSearchArticles(search)
    }
    BackHandler { navController.navigate(NavGroup.BEFORE_SEARCH) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        SearchTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            value = uiState.search,
            onValueChange = viewModel::updateTitle,
            enabled = false,
            hint = "검색어를 입력하세요",
            onClick = { navController.navigate(NavGroup.SEARCHING) },
            onUiClick = { navController.navigate(NavGroup.SEARCHING) }
        )
        Spacer(modifier = Modifier.height(22.dp))
        Box {
            uiState.articles.let { state ->
                when (state) {
                    is FetchFlow.Failure -> Text("서버 오류");
                    is FetchFlow.Fetching -> {
                        LazyColumn {
                            items(5) {
                                JJapList()
                            }
                        }

//                        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
//                            Text(
//                                text = "최근 검색어",
//                                style = boardContent1.copy(fontWeight = FontWeight.Normal)
//                            )
//                            Spacer(modifier = Modifier.height(12.dp))
//                            if (uiState.searchHistory.isNotEmpty()) {
//                                FlowRow(
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    uiState.searchHistory.reversed()
//                                        .forEachIndexed { index, searchHistory ->
//                                            if (index >= 4) return@forEachIndexed
//                                            if (searchHistory.history.isNotEmpty()) {
//                                                SearchHistoryList(
//                                                    content = searchHistory.history,
//                                                    onClick = {
//                                                        keyboardController?.hide()
//                                                        viewModel.updateTitle(searchHistory.history)
//                                                        viewModel.getSearchArticles()
//                                                    }
//                                                )
//                                            }
//                                        }
//                                }
//                                Box(modifier = Modifier.fillMaxWidth().weight(1f).noRippleClickable {
//                                    keyboardController?.hide()
//
//                                })
//                            }
//                        }
                    }
                    is FetchFlow.Success -> {
                        val articlesItems = state.data.collectAsLazyPagingItems()
                        when {
                            articlesItems.loadState.refresh is LoadState.Loading -> {
                                LazyColumn {
                                    items(5) {
                                        JJapList()
                                    }
                                }
                            }
                            articlesItems.loadState.refresh is LoadState.NotLoading -> {
                                if (articlesItems.itemCount != 0) {
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
                                } else {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.Center)
                                    )
                                    {
                                        Image(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .size(180.dp),
                                            painter = painterResource(id = R.drawable.no_article_man),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            text = "글이 없습니다.",
                                            style = caption2
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