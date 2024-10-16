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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.ui.component.items.SearchHistoryList
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.getData()
    }
    Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(scrollState)) {
        BackHandlers(navController = navController)
        Spacer(modifier = Modifier.height(24.dp))
        SearchTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = uiState.search,
            onValueChange = viewModel::updateTitle,
            hint = "검색어를 입력하세요",
            onClick = {
                if(uiState.search.isNotEmpty()){
                    viewModel.addData(uiState.search)
                    viewModel.updateTitle("")
                }

                      },
            keyboardActions = KeyboardActions(onDone = {
                if (uiState.search.isNotEmpty()){
                    viewModel.addData(uiState.search)
                    viewModel.updateTitle("")
                }

            })
        )
        Spacer(modifier = Modifier.height(22.dp))
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(text = "최근 검색어", style = boardContent1.copy(fontWeight = FontWeight.Normal))
            Spacer(modifier = Modifier.height(12.dp))
            if (uiState.searchHistory.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    uiState.searchHistory.reversed().forEachIndexed { index, searchHistory ->
                        if (index >= 4) return@forEachIndexed

                        if (searchHistory.history.isNotEmpty()) {
                            SearchHistoryList(content = searchHistory.history)
                        }
                        // 필요하면 특정 인덱스까지만 보여줄 수 있음
                    }

                }
            }
        }

    }
}