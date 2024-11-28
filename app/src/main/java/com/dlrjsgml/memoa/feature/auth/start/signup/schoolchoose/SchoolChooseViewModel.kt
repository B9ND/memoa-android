package com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose

import android.util.Log
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
    val departmentId: Int? = -1,
    val departmentName: AnnotatedString = buildAnnotatedString { },
    val showBottomSheet: Boolean = false,
    val showDialog: Boolean = false,
    val selectedGrade: String = "1학년",
    val selectedGradeInt: Int = 1,
    val departmentList: List<String> = emptyList(),
    val selectedItem: Int = -1,
    val selectedDepartment: Int = -1,
    val isExpanded: Boolean = false
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

    fun updateExpand(expand: Boolean) {
        _uiState.update { it.copy(isExpanded = expand) }
    }

    fun updateItem(item: Int) {
        _uiState.update {
            it.copy(
                selectedItem = item,
                selectedDepartment = -1,
                departmentList = emptyList()
            )
        }
    }

    fun updateDepItem(item: Int) {
        val currentState = _uiState.value
        val selectedSchool = currentState.response.getOrNull(0)

        val departmentsForGrade = selectedSchool?.departments?.filter {
            it.grade == currentState.selectedGradeInt
        }

        Log.d("shit?", "updateList: ${selectedSchool}")
        Log.d("shit?", "updateList: ${_uiState.value.selectedItem}")
        Log.d("shit?", "updateList: ${currentState.response.getOrNull(0)}")
        val selectedDepartment = departmentsForGrade?.getOrNull(item)
        if (selectedDepartment != null) {
            _uiState.update {
                it.copy(
                    selectedDepartment = item,
                    departmentId = selectedDepartment.id,
                    departmentName = buildAnnotatedString { append(selectedDepartment.name) },
                    isExpanded = false
                )
            }
            Log.d("kmj1", "updateDepItem: $selectedDepartment")
        } else {
            Log.d("Department", "Invalid department selection")
        }
    }




    fun updateList() {
        val currentState = _uiState.value
        val selectedSchool = currentState.response.getOrNull(0)
        Log.d("갑자기?", "updateList: ${currentState.response}")

        val departmentNames = selectedSchool?.departments
            ?.filter { it.grade == currentState.selectedGradeInt }
            ?.map { it.name } ?: emptyList()
        Log.d("갑자기??", "updateList: ${currentState.selectedGradeInt}")
        Log.d("갑자기??", "updateList: ${selectedSchool?.name}")
        Log.d("갑자기??", "updateList: ${selectedSchool?.departments}")


        _uiState.update {
            it.copy(
                departmentList = departmentNames,
                selectedDepartment = if (it.selectedDepartment >= departmentNames.size) -1 else it.selectedDepartment
            )
        }
    }

    fun updateDialog(dialog: Boolean) {
        _uiState.update { it.copy(showDialog = dialog) }
    }

    fun updateShowBottomSheet(sheet: Boolean) {
        _uiState.update { it.copy(showBottomSheet = sheet) }
    }

    fun updateUpdateGrade(grade: String) {
        _uiState.update {
            it.copy(
                selectedGrade = grade,
                selectedGradeInt = grade[0].toString().toInt(),
                selectedDepartment = -1,
                departmentList = emptyList()
            )
        }
    }

    fun updateDepartmentId(departmentId: Int?) {
        _uiState.update {
            it.copy(departmentId = departmentId)
        }
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
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun lastSignup(email: String, nickname: String, password: String, departmentId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (departmentId == -1) {
                    _uiEffect.emit(SignUpSideEffect.Failed)
                    return@launch
                }

                RetrofitClient.signupService.lastSignupSearch(
                    register = SignUpRequest(email, nickname, password, departmentId)
                )
                _uiEffect.emit(SignUpSideEffect.Success)
            } catch (_: HttpException) {
                _uiEffect.emit(SignUpSideEffect.Failed)
            } catch (_: Exception) {
                _uiEffect.emit(SignUpSideEffect.Failed)
            }
        }
    }

    fun schoolSearch(school: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responses = RetrofitClient.getSchoolService.schoolSearch(school)
                _uiState.update { it.copy(response = responses) }
            } catch (e: HttpException) {
                Log.d("signupServer", "Error code: ${e.code()}")
            } catch (e: Exception) {
                Log.d("signupServer", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}
