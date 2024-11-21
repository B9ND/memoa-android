package com.dlrjsgml.memoa.feature.main.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.data.local.bookmark.BookMarkEntity
import com.dlrjsgml.memoa.feature.main.main.paging.ArticlePagingSource
import com.dlrjsgml.memoa.network.main.ArticleResponse
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class ArticlesState(
    val articles: Flow<PagingData<ArticleResponse>> = flowOf(),
)

data class TagState(
    val tags: List<String> = arrayListOf(),
)

sealed interface ArticlesSideEffect {
    data object Success : ArticlesSideEffect
    data object Failure : ArticlesSideEffect
    data object TokenError : ArticlesSideEffect  // 토큰 에러 추가
}

sealed interface BookMarkDoSideEffect {
    data object Success : BookMarkDoSideEffect
    data object Failure : BookMarkDoSideEffect
}


class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState = _uiState.asStateFlow()

    private val _tagUiState = MutableStateFlow(TagState())
    val tagUiState = _tagUiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ArticlesSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _bookMarkUiEffect = MutableSharedFlow<BookMarkDoSideEffect>()
    val bookMarkUiEffect = _bookMarkUiEffect.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                val data = Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false,
                        initialLoadSize = 10
                    ),
                    pagingSourceFactory = { ArticlePagingSource("", _tagUiState.value.tags) }
                ).flow
                    .catch { e ->
                        when (e) {
                            is HttpException -> {
                                when (e.code()) {
                                    402 -> {
                                        // 토큰 관련 에러 처리
                                        Log.e("MainViewModel", "Token Error: ${e.message()}")
                                        _uiEffect.emit(ArticlesSideEffect.TokenError)
                                    }

                                    else -> {
                                        Log.e("MainViewModel", "HTTP Error: ${e.code()}")
                                        _uiEffect.emit(ArticlesSideEffect.TokenError)
                                    }
                                }
                            }

                            else -> {
                                Log.e("MainViewModel", "Error: ${e.message}")
                                _uiEffect.emit(ArticlesSideEffect.Failure)
                            }
                        }
                    }
                    .cachedIn(viewModelScope)
                _uiState.update { it.copy(articles = data) }
                Log.d("인확", "dlrjsgml44 Ok $data");
                _uiEffect.emit(ArticlesSideEffect.Success)
            }
        }
        }
        fun getArticles() {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val data = Pager(
                        config = PagingConfig(
                            pageSize = 10,
                            enablePlaceholders = false,
                            initialLoadSize = 10
                        ),
                        pagingSourceFactory = { ArticlePagingSource("", _tagUiState.value.tags) }
                    ).flow
                        .catch { e ->
                            when (e) {
                                is HttpException -> {
                                    when (e.code()) {
                                        402 -> {
                                            // 토큰 관련 에러 처리
                                            Log.e("MainViewModel", "Token Error: ${e.message()}")
                                            _uiEffect.emit(ArticlesSideEffect.TokenError)
                                        }

                                        else -> {
                                            Log.e("MainViewModel", "HTTP Error: ${e.code()}")
                                            _uiEffect.emit(ArticlesSideEffect.TokenError)
                                        }
                                    }
                                }

                                else -> {
                                    Log.e("MainViewModel", "Error: ${e.message}")
                                    _uiEffect.emit(ArticlesSideEffect.Failure)
                                }
                            }
                        }
                        .cachedIn(viewModelScope)

                    _uiState.update { it.copy(articles = data) }
                    _uiEffect.emit(ArticlesSideEffect.Success)
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error: ${e.message}")
                    _uiEffect.emit(ArticlesSideEffect.Failure)
                }
            }
        }

        fun fillTags(tag: String) {
            _tagUiState.update {
                if (tag in it.tags) {
                    it.copy(tags = it.tags)
                } else {
                    it.copy(tags = arrayListOf(tag))
                }
            }
            getArticles()
        }

        fun bookmark(id: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("북마크", "들어갔음");
                try {
                    val response = RetrofitClient.postBookMarkService.postBookMark(id)
                    _bookMarkUiEffect.emit(BookMarkDoSideEffect.Success)
                    Log.d("북마크", response.toString());

                } catch (e: Exception) {
                    Log.d("북마크", e.message.toString());
                    _bookMarkUiEffect.emit(BookMarkDoSideEffect.Failure)
                }
            }
        }

//    fun bookMarks(articleId: Int) : Boolean{
//        viewModelScope.launch(Dispatchers.IO) {
//            val article = BookMarkEntity(articleId = articleId)
//            return@launch (room!!.bookMarkDao().upsert(article))
//        }
//    }
    }


