package com.dlrjsgml.memoa.feature.main.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.data.local.search.SearchHistoryEntity
import com.dlrjsgml.memoa.data.local.UserDatabase
import com.dlrjsgml.memoa.feature.main.main.paging.ArticlePagingSource
import com.dlrjsgml.memoa.feature.main.main.paging.FetchFlow
import com.dlrjsgml.memoa.network.data.user.getUser.getUserProfile
import com.dlrjsgml.memoa.network.main.ArticleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SearchState(
    val search: String = "",
    val searchHistory: List<SearchHistoryEntity> = emptyList(),
    val tags: List<String> = arrayListOf(),
//    val articles : FetchFlow<Flow<PagingData<ArticleResponse>>> = FetchFlow.Fetching()
    val articles: Flow<PagingData<ArticleResponse>> = flowOf(),

    )

sealed interface SearchSideEffect {
    data object BeforeSearch : SearchSideEffect
    data object Success : SearchSideEffect
    data object Failure : SearchSideEffect
}


class SearchViewModel(
) : ViewModel(

) {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SearchSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    private val room = UserDatabase.getInstance()

    init {
        val user = getUserProfile(MemoaApplication.getContext())
    
        val userSchool = user.department.school
        fillTags(userSchool)
    }

    fun beforeSearch() {
        Log.d("확인", "비포");
        viewModelScope.launch {
            _uiEffect.emit(SearchSideEffect.BeforeSearch)
        }
        Log.d("확인", "${_uiEffect.toString()}");

    }

    fun getSearchArticles(search: String) {
        Log.d("확인", "검색전");

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = Pager(config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false,
                    initialLoadSize = 10
                ),
                    pagingSourceFactory = {
                        ArticlePagingSource(
                            search,
                            _uiState.value.tags
                        )
                    }).flow.cachedIn(viewModelScope)
                Log.d("확인", uiState.value.search);
                _uiState.update { it.copy(articles = data) }
                _uiEffect.emit(SearchSideEffect.Success)
            } catch (e: Exception) {
//                _uiState.update { it.copy(articles = FetchFlow.Failure()) }
                _uiEffect.emit(SearchSideEffect.Failure)
            }
        }
    }

    fun addData(
        searchHistory: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newDataObject = SearchHistoryEntity(
                history = searchHistory
            )
            room!!.searchHistoryDao().insertWithLimit(newDataObject)
            getData()
        }
    }

    fun startFetching() {
//        _uiState.update { it.copy(articles = FetchFlow.Fetching()) }
    }

    fun fillTags(tag: String) {
        _uiState.update {
            if (tag in it.tags) {
                it.copy(tags = it.tags - arrayListOf(tag))
            } else {
                it.copy(tags = it.tags + arrayListOf(tag))
            }
        }
        getSearchArticles(_uiState.value.search)
        Log.d("ㅎㅇ", "${uiState.value.tags.sorted()}");
    }


    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = room!!.searchHistoryDao().getAll()
                updateSearchHistory(data)
                _uiEffect.emit(SearchSideEffect.BeforeSearch)
                Log.d("ㅎㅇ", "$data");
            } catch (e: Exception) {
                Log.d("룸", "룸에러");

            }

        }
    }

    fun updateSearchHistory(searchHistory: List<SearchHistoryEntity>) {
        _uiState.update { it.copy(searchHistory = searchHistory) }
    }

    fun updateTitle(search: String) {
        _uiState.update { it.copy(search = search) }
    }

}