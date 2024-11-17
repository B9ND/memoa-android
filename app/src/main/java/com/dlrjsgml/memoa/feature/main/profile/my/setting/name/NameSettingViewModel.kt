package com.dlrjsgml.memoa.feature.main.profile.my.setting.name

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionSideEffect
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionState
import com.dlrjsgml.memoa.network.profile.ChangeUserNameRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NameState(
    val name: String = "",
)

sealed interface NameSideEffect {
    data object Success : NameSideEffect
    data object Failure : NameSideEffect
}

class NameSettingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NameState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DescriptionSideEffect>()
    val uiEffect: SharedFlow<DescriptionSideEffect> = _uiEffect.asSharedFlow()

    fun changeName() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val request = ChangeUserNameRequest(uiState.value.name)
                val response = RetrofitClient.patchProfileService.changeUserName(request)
                Log.d("내정보 바꾸죠?", "dlrjsgml44 Ok");
                _uiEffect.emit(DescriptionSideEffect.Success)
            } catch (e: Exception) {
                Log.d("내정보 바꾸죠?", "dlrjsgml44 Ok");
                _uiEffect.emit(DescriptionSideEffect.Failure)
            }
        }

    }


    fun updateTitle(name: String) {
        _uiState.update { it.copy(name = name) }
    }

}