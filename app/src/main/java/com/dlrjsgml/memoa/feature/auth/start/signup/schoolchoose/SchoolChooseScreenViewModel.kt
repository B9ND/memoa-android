package com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.school.SchoolSearchResponse
import com.dlrjsgml.memoa.network.data.signup.SignUpRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class TextState(
    val school: String = "",
    val response: List<SchoolSearchResponse> = emptyList(),
    val schoolNames: List<SchoolSearchResponse> = emptyList(),
    val departmentNames: List<String> = emptyList(),
    val departmentId: Int = -1
)

class SchoolChooseScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()

    fun updateSchool(content: String) {
        _uiState.update {
            it.copy(school = content)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responses = RetrofitClient.getSchoolService.schoolSearch(content)
                _uiState.update { it.copy(response = responses) }
                Log.d("TAG", "schoolSearch: ${_uiState.value.schoolNames[0].name} ")
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun lastSignup(email: String, nickname: String, password: String, departmentId: Int){
        Log.d("ㅎㅇ", "lastSignup: $email ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lastResponse = RetrofitClient.signupService.lastSignupSearch(
                    register = SignUpRequest(email, nickname, password, departmentId)
                )
                Log.d("?", "$lastResponse")
            }  catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun updateId(departmentId: Int) {
        _uiState.update {
            it.copy(departmentId = departmentId)
        }
    }

    fun updateResponse(responses: List<SchoolSearchResponse>) {
        _uiState.update {
            it.copy(response = responses)
        }
    }

    fun schoolSearch(school: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responses = RetrofitClient.getSchoolService.schoolSearch(school)
                _uiState.update { it.copy(response = responses) }
                Log.d("TAG", "schoolSearch: ${_uiState.value.schoolNames[0].name} ")
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")

            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}
