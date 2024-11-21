package com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose

import android.util.Log
import androidx.compose.material3.SheetState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.data.school.SchoolSearchResponse
import com.dlrjsgml.memoa.network.data.signup.SignUpRequest
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class TextState(
    val school: String = "",
    val response: List<SchoolSearchResponse> = emptyList(),
    val schoolNames: List<String> = emptyList(),
    val departmentNames: List<String> = emptyList(),
    val departmentId: Int = -1,
    val departmentName: AnnotatedString = buildAnnotatedString { },
    val showBottomSheet: Boolean = false,
    val showDialog: Boolean = false
)

sealed interface SignUpSideEffect {
    data object Success : SignUpSideEffect
    data object Failed : SignUpSideEffect
}

class SchoolChooseScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TextState())
    val uiState = _uiState.asStateFlow()



    private val _uiEffect = MutableSharedFlow<SignUpSideEffect>()
    val uiEffect: SharedFlow<SignUpSideEffect> = _uiEffect.asSharedFlow()

    fun updateDpName() {
        _uiState.update { it.copy(departmentName = buildAnnotatedString {  }) }
    }

    fun updateDialog(dialog: Boolean) {
        _uiState.update { it.copy(showDialog = dialog) }
    }

    fun updateShowBottomSheet(sheet: Boolean) {
        _uiState.update { it.copy(showBottomSheet = sheet) }
    }

    fun updateSchool(content: String) {
        _uiState.update {
            it.copy(school = content)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responses = RetrofitClient.getSchoolService.schoolSearch(content)
                _uiState.update { it.copy(response = responses) }
                _uiState.update { it.copy(schoolNames = responses.map { it.name }) }
                Log.d("터지는놈1", "updateSchool: ${responses[1]}")
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun lastSignup(email: String, nickname: String, password: String, departmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                RetrofitClient.signupService.lastSignupSearch(
                    register = SignUpRequest(email, nickname, password, departmentId)
                )
                _uiEffect.emit(SignUpSideEffect.Success)
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



    fun schoolSearch(school: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responses = RetrofitClient.getSchoolService.schoolSearch(school)
                _uiState.update { it.copy(response = responses) }
                Log.d("터지는놈", "schoolSearch: ${_uiState.value.response}")
                Log.d("터지는놈", "schoolSearch: ${_uiState.value.response[0].departments} ")
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}
