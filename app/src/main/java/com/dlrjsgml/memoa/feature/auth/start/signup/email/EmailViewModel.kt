package com.dlrjsgml.memoa.feature.auth.start.signup.email

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

data class TextState(
    val email: String = "",
    val auth: String = "",
    val welcome: Boolean = false
)


class EmailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(content: String) {
        _uiState.update { it.copy(email = content) }
    }

    fun updateAuth(auth: String) {
        if (auth.length < 7) _uiState.update { it.copy(auth = auth) } else _uiState
    }



    suspend fun sendCode(email: String) {
        return withContext(Dispatchers.IO) {
            try {
                RetrofitClient.getCodeService.sendAuthCode(email = email)
            } catch (e: HttpException) {
                Log.d("sign", e.code().toString())
            }
        }
    }

    fun checkCode(email: String, code: String) {
        viewModelScope.launch {
            try {
                RetrofitClient.sendCodeService.checkAuthCode(email, code)
                _uiState.update { it.copy(welcome = true) }
            } catch (e: HttpException) {
                Log.d("sign", "HttpException: ${e.code()}") // HttpException 처리
            } catch (e: Exception) {
                Log.d("sign", "Exception: ${e.message}") // 다른 예외 처리
                e.printStackTrace() // 예외의 상세 정보 출력
            }
        }
    }

}
