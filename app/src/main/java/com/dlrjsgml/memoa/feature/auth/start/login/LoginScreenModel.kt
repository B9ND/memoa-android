package com.dlrjsgml.memoa.feature.auth.start.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.profile.my.MyProfileEffect
import com.dlrjsgml.memoa.network.data.ApiService
import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
<<<<<<< HEAD:app/src/main/java/com/dlrjsgml/memoa/feature/auth/start/login/LoginViewModel.kt
import com.dlrjsgml.memoa.remote.TemporaryToken
import kotlinx.coroutines.flow.MutableSharedFlow
=======
>>>>>>> origin/feature/SignUpServer:app/src/main/java/com/dlrjsgml/memoa/feature/auth/start/login/LoginScreenModel.kt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class TextState(
    val email: String = "leegeh1213@gmail.com",
    val password: String = "1234"
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

    private val _loginState = MutableStateFlow("")
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
<<<<<<< HEAD:app/src/main/java/com/dlrjsgml/memoa/feature/auth/start/login/LoginViewModel.kt
                val loginData = LoginRequest(email,password)
                val response = apiService.login(loginData)
                Log.d("로그인", "성공 : ${response.access}")
                TemporaryToken.AccessToken = response.access
                _uiEffect.emit(LoginSideEffect.Success)
=======
                val loginRequest = LoginRequest(email, password)
                val response = RetrofitClient.getLoginService.login(loginRequest)
                Log.d("login", "성공 : ${response.access}")
>>>>>>> origin/feature/SignUpServer:app/src/main/java/com/dlrjsgml/memoa/feature/auth/start/login/LoginScreenModel.kt
            } catch (e: HttpException) {
                Log.d("login", e.code().toString())
            }
        }
    }
}



