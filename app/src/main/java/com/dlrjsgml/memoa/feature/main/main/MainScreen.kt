package com.dlrjsgml.memoa.feature.main.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dlrjsgml.memoa.backhandler.HomeBackOnPressed
import com.dlrjsgml.memoa.feature.main.main.deatil.DetailScreen
import com.dlrjsgml.memoa.feature.main.main.paging.MyPagingSource
import com.dlrjsgml.memoa.network.main.ArticleResponse
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.MemoaDropDown
import com.dlrjsgml.memoa.ui.component.items.JJapList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toCollection

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.getArticles()
    }
    val id = 1 // 특정 ID를 사용하여 글 가져오기
    val uiState by viewModel.uiState.collectAsState()
    val lazyPagingItems = uiState.articles.collectAsLazyPagingItems()


    Log.d("글보기 ", "헬로우월ㄷ ${lazyPagingItems.itemCount}");
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    Column {
        HomeBackOnPressed()
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(start = 25.dp, end = 27.dp, top = 10.dp)
                .padding(vertical = 8.dp)
        ) {
            MemoaDropDown(
                selectList = listOf("대구소프트웨어마이스터고등학교", "교학웨트프소구대"),
                modifier = Modifier.weight(6f)
            ) {
            }
            MemoaDropDown(
                selectList = listOf("1학년", "2학년", "3학년"),
                modifier = Modifier.weight(2.4f)

            ) {
            }
        }


        LazyColumn(
//            userScrollEnabled = true
        ) {


            when {
                lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                    items(10) {
                        JJapList()
                    }
                }
                lazyPagingItems.loadState.append is LoadState.Loading -> {
                    items(10) {
                        JJapList()
                    }
                }
            }
            items(lazyPagingItems.itemCount){
                val article = lazyPagingItems[it]
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
                        bookmarkClick = {  },
                        commentClick = {},
                        navController = navController
                    )
                }
            }
        }

    }
}