package com.dlrjsgml.memoa.feature.main.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.write.WriteState
import com.dlrjsgml.memoa.network.bookmark.BookMarkResponse
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookMarkState(
    val tags : List<String> = emptyList(),
    val bookMarks : List<BookMarkResponse> = emptyList()
)

sealed interface BookMarkSideEffect{
    data object Success : BookMarkSideEffect
    data object Failure : BookMarkSideEffect
}

class BookMarkViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookMarkState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<BookMarkSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun getBookMarks(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.getBookMarkService.getBookMark()
                _uiState.update {
                    it.copy(bookMarks = response)
                }
                _uiEffect.emit(BookMarkSideEffect.Success)

            } catch (e:Exception){
                _uiEffect.emit(BookMarkSideEffect.Failure)

            }

        }
    }
}