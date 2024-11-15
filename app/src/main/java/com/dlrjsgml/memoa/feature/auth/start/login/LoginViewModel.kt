package com.dlrjsgml.memoa.feature.auth.start.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.user.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveRefToken
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class TextState(
    val email: String = "kmjmj8769@gmail.com",
    val password: String = "kmj8769@",
    val access: String = "",
    val refresh: String = ""
)


sealed interface LoginSideEffect {
    data object Success : LoginSideEffect
    data object Failed : LoginSideEffect
}

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()


    private val _uiEffect = MutableSharedFlow<LoginSideEffect>()
    val uiEffect: SharedFlow<LoginSideEffect> = _uiEffect.asSharedFlow()

    fun updateEmail(content: String) {
        _uiState.update { it.copy(email = content) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updateToken(access: String, refresh: String) {
        _uiState.update { it.copy(access = access, refresh = refresh) }
    }


    private val _loginState = MutableStateFlow("")
    val loginState = _loginState.asStateFlow()
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginData = LoginRequest(email, password)
                val response = RetrofitClient.getLoginService.login(loginData)
                updateToken(response.access, response.refresh)
                _uiEffect.emit(LoginSideEffect.Success)
            } catch (e: HttpException) {
                Log.d("login", e.code().toString())
            }
        }
    }

    fun saveTokens(context: Context){
        saveAccToken(context,uiState.value.access)
        saveRefToken(context,uiState.value.refresh)
        Log.d("토큰 새로 발급 받은거", "saveTokens: ${uiState.value.access}")
        Log.d("토큰 새로 발급 받은거", "saveTokens: ${uiState.value.refresh}")
    }
}



