package com.dlrjsgml.memoa.feature.auth.start.signup.password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StringState(
    val password: String = "",
)

class PasswordScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StringState())
    val uiState = _uiState.asStateFlow()

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}