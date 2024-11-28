package com.dlrjsgml.memoa.feature.auth.start.login

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.feature.auth.start.start.NetworkErrorHandler
import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.user.saveUser.saveAccToken
import com.dlrjsgml.memoa.network.data.user.saveUser.saveRefToken
import com.dlrjsgml.memoa.remote.NetworkUtil
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
    val email: String = "leegeh1213@dgsw.hs.kr",
    val password: String = "1234",
    val access: String = "",
    val refresh: String = "",
    val error: String = "",
    val showDialog: Boolean = false,
    val loadingState: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface LoginSideEffect {
    data object Success : LoginSideEffect
    data object Failed : LoginSideEffect
}

class LoginViewModel(
) : ViewModel() {
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

    fun updateError(error: String) {
        _uiState.update { it.copy(error = error) }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = "")
    }

    fun updateDialog(show: Boolean) {
        _uiState.update { it.copy(showDialog = show) }
    }

    fun updateLoadingState(show: Boolean) {
        _uiState.update { it.copy(loadingState = show) }
    }

    fun login(email: String, password: String, networkUtil: NetworkUtil) {
        if (email.length <= 255 && password.length <= 255) {
            if (!networkUtil.isNetworkConnected()) {
                updateLoadingState(true)
            } else {
                _uiState.update { it.copy(loadingState = false) }
                viewModelScope.launch {
                    try {
                        val loginData = LoginRequest(email, password)
                        val response = RetrofitClient.getLoginService.login(loginData)
                        updateToken(response.access, response.refresh)
                        _uiEffect.emit(LoginSideEffect.Success)
                        updateDialog(false)
                    } catch (e: HttpException) {
                        _uiEffect.emit(LoginSideEffect.Failed)
                        NetworkErrorHandler.handle(MemoaApplication.getContext(), e)
                        updateDialog(true)
                        if (e.code() == 401) {
                            updateError("아이디 또는 비밀번호가 일치하지 않습니다.")
                            viewModelScope.launch {
                                try {
                                    _uiState.update { it.copy(isLoading = true) }
                                    val loginData = LoginRequest(email, password)
                                    val response = RetrofitClient.getLoginService.login(loginData)
                                    updateToken(response.access, response.refresh)
                                    _uiEffect.emit(LoginSideEffect.Success)
                                    updateDialog(false)
                                } catch (e: HttpException) {
                                    _uiEffect.emit(LoginSideEffect.Failed)
                                    updateDialog(true)
                                    if (e.code() == 401) {
                                        updateError("아이디 또는 비밀번호가 일치하지 않습니다.")
                                        Log.d("뷰모델쪽", "login: ${e.code()}")
                                    } else {
                                        if (e.code() == 400) {
                                            updateError("유효하지 않은 이메일 입니다.")
//여기 고쳐야함
                                            Log.d("뷰모델쪽", "login: ${e.code()}")
                                        } else {
                                            if (e.code() == 400) {
                                                updateError("유효하지 않은 이메일 입니다.")
                                                Log.d("뷰모델쪽", "login: ${e.code()}")
                                            } else {
                                                if (e.code() == 406) {
                                                    updateError("현재 서버가 동작하지 않습니다.\n잠시후 다시 시도해 주세요.")
                                                    Log.d("뷰모델쪽", "login: ${e.code()}")
                                                }
                                            }
                                        }
                                    }
                                    Log.d("뷰모델쪽", "login: ${_uiState.value.error}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    fun saveTokens(context: Context) {
        saveAccToken(context, uiState.value.access)
        saveRefToken(context, uiState.value.refresh)
    }
}



