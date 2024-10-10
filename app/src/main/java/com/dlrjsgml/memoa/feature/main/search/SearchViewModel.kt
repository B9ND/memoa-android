package com.dlrjsgml.memoa.feature.main.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.data.local.SearchHistoryEntity
import com.dlrjsgml.memoa.data.local.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SearchState(
    val search: String = "",
    val searchHistory: List<SearchHistoryEntity> = emptyList(),
)

class SearchViewModel(
) : ViewModel(

) {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    val db = UserDatabase.getInstance()

    fun addData(
        searchHistory: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newDataObject = SearchHistoryEntity(
                history = searchHistory
            )
            db!!.searchHistoryDao().insert(newDataObject)
            getData()

        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO) {
            db!!.searchHistoryDao().deleteAll()
            getData()
        }

    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = db!!.searchHistoryDao().getAll()
            updateSearchHistory(data)
            Log.d("ㅎㅇ", "${data}");
        }
    }

    fun updateSearchHistory(searchHistory: List<SearchHistoryEntity>) {
        _uiState.update { it.copy(searchHistory = searchHistory) }
    }



    fun updateTitle(search: String) {
        _uiState.update { it.copy(search = search) }
    }

}