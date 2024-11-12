package com.dlrjsgml.memoa.feature.auth.start.signup.email

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.ApiService
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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

    fun getEmail(): String {
        return uiState.value.email
    }



    suspend fun sendCode(email: String) {
        return withContext(Dispatchers.IO) {
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.getCodeService.sendAuthCode(email = email)
                } catch (e: HttpException) {
                    Log.d("sign", e.code().toString())
                }
            }
        }
    }
    suspend fun checkCode(email: String, code: String) {
        return withContext(Dispatchers.IO) {
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.sendCodeService.checkAuthCode(email, code)
                    _uiState.update { it.copy(welcome = true) }

                } catch (e: HttpException) {
                    Log.d("sign", e.code().toString())
                }
            }
        }
    }
}
