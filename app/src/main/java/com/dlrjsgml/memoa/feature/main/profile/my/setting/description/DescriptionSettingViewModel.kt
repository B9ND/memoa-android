package com.dlrjsgml.memoa.feature.main.profile.my.setting.description 

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.profile.ChangeUserDescriptionRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DescriptionState(
    val description: String = "",
)

sealed interface DescriptionSideEffect {
    data object Success : DescriptionSideEffect
    data object Failure : DescriptionSideEffect
}

class DescriptionSettingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DescriptionState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DescriptionSideEffect>()
    val uiEffect: SharedFlow<DescriptionSideEffect> = _uiEffect.asSharedFlow()

    fun changeName() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val request = ChangeUserDescriptionRequest(uiState.value.description)
                val response = RetrofitClient.patchProfileService.changeUserDescription(request)
                Log.d("내정보 바꾸죠?", "dlrjsgml44 Ok");
                _uiEffect.emit(DescriptionSideEffect.Success)
            } catch (e: Exception) {
                Log.d("내정보 바꾸죠?", "dlrjsgml44 Ok");
                _uiEffect.emit(DescriptionSideEffect.Failure)
            }
        }

    }


    fun updateTitle(description: String) {
        _uiState.update { it.copy(description = description) }
    }

}