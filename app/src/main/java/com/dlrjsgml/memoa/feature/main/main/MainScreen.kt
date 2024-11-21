package com.dlrjsgml.memoa.feature.main.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.HomeBackOnPressed
import com.dlrjsgml.memoa.backhandler.safePopBackStack
import com.dlrjsgml.memoa.feature.main.write.UpLoadImageSideEffect
import com.dlrjsgml.memoa.feature.main.write.WriteSideEffect
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.MemoaDropDown
import com.dlrjsgml.memoa.ui.component.items.JJapList
import com.dlrjsgml.memoa.ui.theme.Gray10
import com.dlrjsgml.memoa.ui.theme.caption1
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.caption2
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController,
) {

    val lazyState = rememberLazyListState()
    val id = 1 // 특정 ID를 사용하여 글 가져오기
    val uiState by viewModel.uiState.collectAsState()
    val lazyPagingItems = uiState.articles.collectAsLazyPagingItems()
    val pullRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    if (pullRefreshState.isRefreshing) {
        viewModel.getArticles()
        pullRefreshState.endRefresh()
    }

    Log.d("ㅎㅇ", "dlrjsgml44 Ok ${lazyPagingItems.loadState}");
    val density = LocalDensity.current
    Log.d("상태", "지금은 : ${lazyPagingItems.itemCount}");

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                ArticlesSideEffect.Failure -> {
                    Log.d("상태", "지금은  실패! : ${lazyPagingItems.itemCount}");

                    navController.safePopBackStack()
                    navController.navigate(NavGroup.START)
                }
                ArticlesSideEffect.Success -> {
                    Log.d("상태", "지금은  성공러 : ${lazyPagingItems.itemCount}");

                }
                ArticlesSideEffect.TokenError -> {
                    Log.d("상태", "지금은  토큰에러 : ${lazyPagingItems.itemCount}");

                    navController.safePopBackStack()
                    navController.navigate(NavGroup.START)
                }
            }
        }
    }
    Scaffold(topBar = {
        AnimatedVisibility(
            visible = lazyState.isScrollingUp().value,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.5f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 4.dp)
                        .padding(vertical = 4.dp, horizontal = 14.dp)
                ) {
                    MemoaDropDown(
                        selectList = listOf("대구소프트웨어마이스터고등학교", "교학웨트프소구대"),
                        modifier = Modifier.weight(5.5f)
                    ) {
                        viewModel.fillTags(it)
                    }
                    MemoaDropDown(
                        selectList = listOf("국어", "영어", "수학", "사회", "과학", "기타"),
                        modifier = Modifier.weight(1.85f)
                    ) {
                        viewModel.fillTags(it)
                    }
                    MemoaDropDown(
                        selectList = listOf("1학년", "2학년", "3학년"),
                        modifier = Modifier.weight(2.1f)
                    ) {
                        viewModel.fillTags(it)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray10)
                )
            }

        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        Box(
            modifier = Modifier
                .nestedScroll(pullRefreshState.nestedScrollConnection)
                .padding(innerPadding)
        ) {
            Column(
            ) {
                HomeBackOnPressed()
                LazyColumn(
                    state = lazyState
//            userScrollEnabled = true
                ) {
                    when {
                        lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                            items(10) {
                                JJapList()
                            }
                        }

                        lazyPagingItems.loadState.refresh is LoadState.NotLoading -> {
                            if (lazyPagingItems.itemCount != 0) {
                                items(lazyPagingItems.itemCount) {
                                    val article = lazyPagingItems[it]
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
                                            bookmarked = article.isBookmarked,
                                            onProfileClick = { navController.navigate("${NavGroup.USERPROFILE}?${article.author}") },
                                            onBookmarkClick = { viewModel.bookmark(article.id) },
                                            onCommentClick = {},
                                            onArticleClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") },
                                            onImageClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") }
                                        )
                                    }
                                }
                            } else {
                                items(1) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    {
                                        Spacer(modifier = Modifier.height(200.dp))
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
//                    lazyPagingItems.loadState.append is LoadState.Loading -> {
//                        items(10) {
//                            JJapList()
//                        }
//                    }
                    }

                }
            }
            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = Color.White,
                contentColor = Color.Black
            )
        }

    }

}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScroll) ->
            if (currentIndex != lastIndex || currentScroll != lastScroll) {
                value = currentIndex < lastIndex ||
                        (currentIndex == lastIndex && currentScroll < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScroll
            }
        }
    }
}

@Preview
@Composable
private fun adfjkafdjkadfjkadfkj() {
    MainScreen(
        viewModel = viewModel(),
        navController = rememberNavController()
    )
}