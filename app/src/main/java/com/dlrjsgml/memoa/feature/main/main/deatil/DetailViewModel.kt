package com.dlrjsgml.memoa.feature.main.main.deatil

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow


sealed interface WriteSideEffect {
    data object Success: WriteSideEffect
    data object Failure: WriteSideEffect
}

class DetailViewModel : ViewModel() {

//    private val _uiEffect = MutableSharedFlow<WriteSideEffect>()
//    val uiEffect = _uiEffect.asSharedFlow()
//
//    private val _uiState = MutableStateFlow(WriteState())
//    val uiState = _uiState.asStateFlow()
}