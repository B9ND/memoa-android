package com.dlrjsgml.memoa.feature.main.profile.my.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.user.saveUser.saveRefToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface SettingSideEffect{
    data object LogoutSuccess :  SettingSideEffect
    data object LogoutFailed :  SettingSideEffect

}
class SettingViewModel : ViewModel() {
    private val _uiEffect = MutableSharedFlow<SettingSideEffect>()
    val uiEffect: SharedFlow<SettingSideEffect> = _uiEffect.asSharedFlow()

    fun logout(context: Context){
        viewModelScope.launch(Dispatchers.IO){
            try{
                saveRefToken(context,null)
                _uiEffect.emit(SettingSideEffect.LogoutSuccess)
            } catch (e:Exception){

                _uiEffect.emit(SettingSideEffect.LogoutFailed)
            }
        }

    }
}