package com.dlrjsgml.memoa.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.data.ApiService
import com.dlrjsgml.memoa.feature.data.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class TextState(
    val email: String = "",
    val password: String = ""
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

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://13.125.84.202")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val _loginState = MutableStateFlow("")
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = apiService.login(LoginRequest(email, password))
            if (response?.isSuccessful == "200") {
                Log.d("login", "s")
            } else {
                Log.d("login", "n")
            }
        }
    }
}



