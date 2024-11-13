package com.dlrjsgml.memoa.feature.main.search.ing

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.search.SearchViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.items.SearchHistoryList
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import com.dlrjsgml.memoa.ui.theme.boardContent1

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchingScreen(
    viewModel: SearchViewModel = viewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    LaunchedEffect(Unit) {
        viewModel.getData()
        viewModel.beforeSearch()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
//        BackHandlers(navController)
        BackHandler { navController.popBackStack() }
        Spacer(modifier = Modifier.height(24.dp))
        SearchTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .focusRequester(focusRequester),
            value = uiState.search,
            onValueChange = viewModel::updateTitle,
            hint = "검색어를 입력하세요",
            onClick = {
                if (uiState.search.isNotEmpty()) {
                    keyboardController?.hide()
                    navController.popBackStack()
                    navController.navigate("${NavGroup.SEARCH}?${uiState.search}")
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                if (uiState.search.isNotEmpty()) {
                    keyboardController?.hide()
                    navController.popBackStack()
                    navController.navigate("${NavGroup.SEARCH}?${uiState.search}")

                }
            })
        )
        Spacer(modifier = Modifier.height(22.dp))
        Box {
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
                                            keyboardController?.hide()
//                                            viewModel.updateTitle(searchHistory.history)
//                                            viewModel.getSearchArticles()
                                        }
                                    )
                                }
                            }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .noRippleClickable {
                            keyboardController?.hide()
                        })
                }
            }
        }
    }
}