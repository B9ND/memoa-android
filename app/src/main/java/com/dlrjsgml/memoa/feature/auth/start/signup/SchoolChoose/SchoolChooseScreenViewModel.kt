package com.dlrjsgml.memoa.feature.auth.start.signup.SchoolChoose

import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TextState(
    val school: String = "",
)

class SchoolChooseScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateSchool(content: String) {
        _uiState.update { it.copy(school = content) }
    }
}