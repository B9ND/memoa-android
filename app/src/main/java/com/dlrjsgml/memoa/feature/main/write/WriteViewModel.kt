package com.dlrjsgml.memoa.feature.main.write

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class WriteState(
    val title : String = "",
    val content : String = "",
    val tags : List<String> = emptyList()
)
sealed interface WriteSideEffect {
    data object Success: WriteSideEffect
    data object Failure: WriteSideEffect
}

class WriteViewModel : ViewModel() {
    private val _uiEffect = MutableSharedFlow<WriteSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(WriteState())
    val uiState = _uiState.asStateFlow()

    fun fillTags(tag: String) {
        _uiState.update {
            if(tag in it.tags){
                it.copy(tags = it.tags - arrayListOf(tag))
            }
            else{
                it.copy(tags = it.tags + arrayListOf(tag))
            }
        }
        Log.d("ㅎㅇ", "${uiState.value.tags.sorted()}");
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}