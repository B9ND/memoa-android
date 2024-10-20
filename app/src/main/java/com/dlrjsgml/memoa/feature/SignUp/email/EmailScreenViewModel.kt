package com.dlrjsgml.memoa.feature.SignUp.email

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TextState(
    val email: String = "",
    val auth: String = ""
)



class EmailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(content: String) {
        _uiState.update { it.copy(email = content) }
    }
    fun updatePAuth(auth: String) {
        if (auth.length < 7) _uiState.update { it.copy(auth = auth) } else _uiState
    }
}
