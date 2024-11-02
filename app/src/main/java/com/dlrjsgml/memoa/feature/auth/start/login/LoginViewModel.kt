package com.dlrjsgml.memoa.feature.auth.start.login

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.ApiService
import com.dlrjsgml.memoa.network.data.LoginRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.remote.TemporaryToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    private val retrofit = RetrofitClient.instance

    private val apiService = retrofit.create(ApiService::class.java)

    private val _loginState = MutableStateFlow("")
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
<<<<<<< HEAD
                val loginData = LoginRequest(email,password)
                val response = apiService.login(loginData)
                Log.d("로그인", "성공 : ${response.access}")
                TemporaryToken.AccessToken = response.access

=======
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)
                Log.d("login", "성공 : ${response.access}")
>>>>>>> 37565d5 (feat: SignUpAuth)

            } catch (e: HttpException) {
                Log.d("login", e.code().toString())
            }
        }
    }
}



