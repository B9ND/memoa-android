package com.dlrjsgml.memoa.feature.main.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dlrjsgml.memoa.feature.main.write.WriteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BookMarkState(
    val tags : List<String> = emptyList()
)

class BookMarkViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookMarkState())
    val uiState = _uiState.asStateFlow()
    fun fillTags(tag: String) {
        _uiState.update {
            if(tag in it.tags){
                it.copy(tags = it.tags - arrayListOf(tag).toSet())
            }
            else{
                it.copy(tags = it.tags + arrayListOf(tag))
            }
        }
        Log.d("ㅎㅇ", "${uiState.value.tags.sorted()}");
    }
}