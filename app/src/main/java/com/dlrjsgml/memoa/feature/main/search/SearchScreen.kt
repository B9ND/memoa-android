package com.dlrjsgml.memoa.feature.main.search


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.dlrjsgml.memoa.ui.component.MemoaCheckBox
import com.dlrjsgml.memoa.ui.component.MemoaDropDown
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
    val articlesItems = uiState.articles.collectAsLazyPagingItems()
    val selectTags = arrayListOf("국어", "영어", "수학", "사회", "과학", "기타")

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.getSearchArticles(search)
        viewModel.updateTitle(search)
    }
    BackHandler { navController.navigate(NavGroup.BEFORE_SEARCH) }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = 18.dp),
                value = uiState.search,
                onValueChange = viewModel::updateTitle,
                enabled = false,
                hint = "검색어를 입력하세요",
                onClick = { navController.navigate(NavGroup.SEARCHING) },
                onUiClick = { navController.navigate(NavGroup.SEARCHING) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 21.dp)

            ) {
                Row {
                    MemoaCheckBox(
                        modifier = Modifier.weight(1f),
                        text = "대구소프트웨어마이스터고등학교",
                        onClick = {}
                    )
                    MemoaDropDown(
                        selectList = listOf("1학년", "2학년", "3학년"),
                        modifier = Modifier.weight(0.3f)
                    ) {
                        viewModel.fillTags(it)
                    }
                }

                Row {
                    LazyRow(
                        modifier = Modifier
                    ) {
                        items(selectTags.size) {
                            MemoaCheckBox(
                                text = selectTags[it],
                                onClick = { viewModel.fillTags(selectTags[it]) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        uiState.articles.let { state ->
            when {
                articlesItems.loadState.refresh is LoadState.Loading -> {
                    items(5) {
                        JJapList()
                    }
                }

                articlesItems.loadState.refresh is LoadState.NotLoading -> {
                    if (articlesItems.itemCount != 0) {
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
                    } else {
                        item {
                            Box(modifier = Modifier.fillMaxSize()) {
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

