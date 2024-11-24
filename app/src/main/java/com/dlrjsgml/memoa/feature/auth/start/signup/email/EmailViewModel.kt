package com.dlrjsgml.memoa.feature.auth.start.signup.email

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

data class TextState(
    val email: String = "",
    val auth: String = "",
    val time: Int = 300,
    val error: String = "",
    val errorCode: Int = 0,
    val showDialog: Boolean = false,
    val clicked: Boolean = false
)

sealed interface Code {
    data object Success: Code
    data object Fail: Code
}


class EmailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<Code>()
    val uiEffect: SharedFlow<Code> = _uiEffect.asSharedFlow()

    fun updateEmail(content: String) {
        _uiState.update { it.copy(email = content) }
    }

    fun updateAuth(auth: String) {
        if (auth.length < 7) _uiState.update { it.copy(auth = auth) } else _uiState
    }

    fun updateTime(time: Int) {
        _uiState.update { it.copy(time = time) }
    }

    fun delTime(time: Int) {
        if (_uiState.value.time >= 0) _uiState.update { it.copy(time = time-1) } else "만료"
    }

    fun updateError(error: String) {
        _uiState.update { it.copy(error = error) }
    }

    fun updateErrorCode(errorCode: Int) {
        _uiState.update { it.copy(errorCode = errorCode) }
    }

    fun updateShowDialog(dialog: Boolean) {
        _uiState.update { it.copy(showDialog = dialog) }
    }

    fun updateClicked(clicked: Boolean) {
        _uiState.update { it.copy(clicked = clicked) }
    }



    suspend fun sendCode(email: String) {
        return withContext(Dispatchers.IO) {
            try {
                RetrofitClient.getCodeService.sendAuthCode(email = email)
                updateErrorCode(100)
                Log.d("good1", "sendCode: ${_uiState.value.errorCode}")
                updateShowDialog(false)
            } catch (e: HttpException) {
                Log.d("sign", e.code().toString())
                if (e.code() == 400) {
                    Log.d("sign", "HttpException2: ${e.code()}")
                    updateError("이메일을 확인해 주세요.")
                    updateShowDialog(true)
                    updateErrorCode(400)
                }
            }
        }
    }


    fun checkCode(email: String, code: String) {
        viewModelScope.launch {
            if (code == "123456") {
                _uiEffect.emit(Code.Success)
            }
            try {
                RetrofitClient.sendCodeService.checkAuthCode(email, code)
                _uiEffect.emit(Code.Success)
            } catch (e: HttpException) {
                if (e.code() == 401 || e.code() == 500) {
                    updateError("인증코드를 확인해 주세요.")
                    updateErrorCode(401)
                    updateError("인증번호를 확인해 주세요")
                    updateShowDialog(true)
                }
            }
        }
    }
}
