package com.dlrjsgml.memoa.feature.main.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dlrjsgml.memoa.feature.main.main.paging.ArticlePagingSource
import com.dlrjsgml.memoa.network.main.ArticleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ArticlesState(
    val articles : Flow<PagingData<ArticleResponse>> = flowOf()
)

sealed interface ArticlesSideEffect{
    data object Success : ArticlesSideEffect
    data object Failure : ArticlesSideEffect
}

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ArticlesSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    fun getArticles(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = Pager(config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false,
                    initialLoadSize = 10
                ),
                    pagingSourceFactory = {ArticlePagingSource("")}).flow.cachedIn(viewModelScope)
                _uiState.update { it.copy(articles = data) }

                _uiEffect.emit(ArticlesSideEffect.Success)
            } catch (e:Exception){
                _uiEffect.emit(ArticlesSideEffect.Failure)
            }
        }
    }



}


