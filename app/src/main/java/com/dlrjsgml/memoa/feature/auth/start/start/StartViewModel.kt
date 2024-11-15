package com.dlrjsgml.memoa.feature.auth.start.start

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dlrjsgml.memoa.network.data.user.getRefToken
import com.dlrjsgml.memoa.network.token.AccTokenRequest
import com.dlrjsgml.memoa.network.token.AccTokenResponse
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Data(
    val access: String = "",
    val refresh: String = ""
)


class StartViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(Data())
    val uiState = _uiState.asStateFlow()

    private fun updateToken(access: String, refresh: String) {
        _uiState.update { it.copy(access = access, refresh = refresh) }
    }

    suspend fun accToken(context: Context) {
        try {
            val tokenData = AccTokenRequest(getRefToken(context))
            val response = RetrofitClient.tokenService.token(tokenData)
            updateToken(response.access, response.refresh)
            Log.d("스타트뷰모델성공", "accToken: ${uiState.value.access}")
            Log.d("스타트뷰모델", "accToken: ${uiState.value.refresh}")
        } catch (e: Exception) {
            Log.d("스타트뷰모델실패", "accToken: ${uiState.value.access}")
            Log.d("스타트뷰모델", "accToken: ${uiState.value.refresh}")
        }

    }
}