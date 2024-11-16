package com.dlrjsgml.memoa.feature.main.profile.my.setting.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NameState(
    val name: String = "",
)

class NameSettingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NameState())
    val uiState = _uiState.asStateFlow()


    fun changeName() {
        try{
             viewModelScope.launch(Dispatchers.IO) {

//                 val response = RetrofitClient.patchProfileService.changeUserInfo()
             }
        } catch (e:Exception){
            throw e
        }
    }


    fun updateTitle(name: String) {
        _uiState.update { it.copy(name = name) }
    }

}