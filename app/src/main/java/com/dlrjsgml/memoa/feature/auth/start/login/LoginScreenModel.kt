package com.dlrjsgml.memoa.feature.auth.start.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.ApiService
import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class TextState(
    val email: String = "leegeh1213@gmail.com",
    val password: String = "1234"
)



class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(content: String) {
        _uiState.update { it.copy(email = content) }
    }
    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private val _loginState = MutableStateFlow("")
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = RetrofitClient.getLoginService.login(loginRequest)
                Log.d("login", "성공 : ${response.access}")
            } catch (e: HttpException) {
                Log.d("login", e.code().toString())
            }
        }
    }
}



