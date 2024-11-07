package com.dlrjsgml.memoa.feature.auth.start.signup.name

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TextState(
    val name: String = "",
)

class NameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateName(content: String) {
        _uiState.update { it.copy(name = content) }
    }
}