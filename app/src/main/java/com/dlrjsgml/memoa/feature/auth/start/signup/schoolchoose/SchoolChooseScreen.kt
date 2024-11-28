package com.dlrjsgml.memoa.feature.auth.start.signup.schoolchoose

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.network.data.school.Department
import com.dlrjsgml.memoa.network.data.school.SchoolSearchResponse
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.button.SchoolButton
import com.dlrjsgml.memoa.ui.component.dialog.dialog
import com.dlrjsgml.memoa.ui.component.items.DepartmentList
import com.dlrjsgml.memoa.ui.component.items.SchoolList
import com.dlrjsgml.memoa.ui.component.textfield.MemoaDropDownTextField
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchoolChooseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SchoolChooseScreenViewModel,
    email: String,
    password: String,
    nickname: String
) {
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val touchScope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                SignUpSideEffect.Success -> {
                    navController.navigate(NavGroup.START)
                }

                SignUpSideEffect.Failed -> {
                    viewModel.updateDialog(true)
                }
            }
        }
    }
    LaunchedEffect(uiState.showBottomSheet) {
        if (uiState.showBottomSheet) {
            coroutineScope.launch {
                sheetState.show()
            }
        } else {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }
    val authString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.text_black)

            )
        )
        {
            append("계정을 생성함으로써,\n")
        }
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.auth_text)
            )
        ) {
            append("이용약관")
        }
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.text_black)
            )
        )
        {
            append("과")
        }
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.auth_text)
            )
        ) {
            append("개인정처리약관")
        }
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.text_black)
            )
        )
        {
            append("에 동의하셨음을 확인합니다.")
        }
    }
    val schoolText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D6D6D)
            )
        ) {
            append("소속학교")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                color = Color(0xFF6D6D6D)
            )
        ) {
            append("를 선택하세요")
        }
    }
    val subjectText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D6D6D)
            )
        ) {
            append("학과")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                color = Color(0xFF6D6D6D)
            )
        ) {
            append("를 선택하세요")
        }
    }
    if (uiState.showDialog) {
        dialog(
            onDismiss = { viewModel.updateDialog(false) },
            text = "학교 또는 학과를 선택해 주세요",
            buttonText = "확인"
        )
    }
    LaunchedEffect(uiState.selectedItem) {
    }

    Box(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF5B48D0),
                        Color(0xFF9C8CFF)
                    )
                )
            )
            .addFocusCleaner(focusManager)
    ) {
        Image(
            painter = painterResource(R.drawable.goorm),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(1024.dp)
                .fillMaxSize()
                .offset(y = 250.dp)
        )
        Box(
            modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 25.dp)
        ) {
            BackButtonWhite {
                navController.popBackStack()
            }
            Column(
                modifier
                    .wrapContentSize()
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = "회원가입",
                    fontSize = 25.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "학년을 선택해 주세요",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                    Spacer(Modifier.height(20.dp))
                    Row {
                        SchoolButton(
                            text = "1학년",
                            isSelected = uiState.selectedGrade == "1학년"
                        ) { isSelected ->
                            if (isSelected) {
                                viewModel.updateUpdateGrade("1학년")
                            }

                        }
                        Spacer(Modifier.width(10.dp))
                        SchoolButton(
                            text = "2학년",
                            isSelected = uiState.selectedGrade == "2학년"
                        ) { isSelected ->
                            if (isSelected) {
                                viewModel.updateUpdateGrade("2학년")
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        SchoolButton(
                            text = "3학년",
                            isSelected = uiState.selectedGrade == "3학년"
                        ) { isSelected ->
                            if (isSelected) {
                                viewModel.updateUpdateGrade("3학년")
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    MemoaTextField(
                        firstFocus = false,
                        value = uiState.school,
                        onValueChange = viewModel::updateSchool,
                        hint = schoolText,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    viewModel.updateShowBottomSheet(true)
                                    viewModel.updateExpand(false)
                                    viewModel.schoolSearch(uiState.school)
                                }
                            )
                            .padding(horizontal = 10.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    MemoaDropDownTextField(
                        hint = when (
                            uiState.selectedDepartment) {
                            -1 -> {
                                subjectText
                            }
                            in 0 until uiState.departmentList.size -> {
                                buildAnnotatedString {
                                    append(uiState.departmentList[uiState.selectedDepartment])
                                }
                            }
                            else -> {
                                subjectText
                            }
                        },
                        modifier = Modifier.clickable {
                            viewModel.updateExpand(true)
                        }
                    )
                    Spacer(Modifier.height(3.dp))
                    Log.d("갑자기", "SchoolChooseScreen: ${uiState.isExpanded}")
                    Log.d("갑자기", "SchoolChooseScreen: ${uiState.selectedItem}")
                    if (uiState.isExpanded && uiState.selectedItem != -1) {
                        LazyColumn {
                            viewModel.updateList()
                            items(count = uiState.departmentList.size) { index ->
                                DepartmentList(
                                    modifier = Modifier
                                        .clickable {
                                            Log.d("kmj", "SchoolChooseScreen: $index")
                                            viewModel.updateDepItem(index)
                                            viewModel.updateExpand(false)
                                            if (uiState.selectedDepartment != -1 && uiState.selectedDepartment != index) {
                                                val selectedId = getDepartNames(
                                                    response = uiState.response,
                                                    name = uiState.response[0].name,
                                                    grade = uiState.selectedGradeInt
                                                )?.get(uiState.selectedDepartment)?.id
                                                selectedId?.let { viewModel.updateDepartmentId(it) }
                                                selectedId?.let { viewModel.updateDepartmentId(it) }
                                                viewModel.updateDpName()
                                            } else {
                                                Log.d("Debug", "No department selected")
                                            }
                                            viewModel.updateDepItem(index)
                                        },
                                    text = uiState.departmentList[index],
                                    top = index == 0,
                                    bottom = index == uiState.departmentList.size - 1,
                                    selected = index == uiState.selectedDepartment
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier.align(alignment = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = authString,
                    textAlign = TextAlign.Center,
                )
                MemoaButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    text = "회원가입",
                    enabled = true,
                ) {
                    if (uiState.selectedDepartment != -1 && uiState.selectedItem != -1) {
                        viewModel.lastSignup(
                            email = email,
                            nickname = nickname,
                            password = password,
                            departmentId = uiState.departmentId
                        )
                    } else {
                        viewModel.updateDialog(true)
                    }
                }
            }
            if (uiState.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.updateShowBottomSheet(false) },
                    sheetState = sheetState,
                    contentColor = Color.White,
                    containerColor = Color.White
                ) {
                    SearchTextField(
                        value = uiState.school,
                        onValueChange = viewModel::updateSchool,
                        hint = schoolText.toString(),
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        onClick = {
                            touchScope.launch {
                                viewModel.schoolSearch(uiState.school)
                            }
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    Spacer(
                        Modifier
                            .height(1.dp)
                            .background(Color(0xFFF0F0F0))
                            .fillMaxWidth()
                    )
                    LazyColumn {
                        items(count = uiState.schoolNames.size) { index ->
                            SchoolList(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.updateItem(index)
                                        viewModel.updateSchool(uiState.schoolNames[index])
                                        viewModel.updateShowBottomSheet(false)
                                    },
                                schoolName = uiState.schoolNames[index],
                            )
                        }
                    }
                    Spacer(Modifier.height(30.dp))
                }
            }
        }
    }
}

fun Modifier.addFocusCleaner(
    focusManager: FocusManager,
    doOnClear: () -> Unit = {}
): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

fun getDepartNames(
    response: List<SchoolSearchResponse>,
    name: String,
    grade: Int
): List<Department>? {
    return response.find { it.name == name }?.departments?.filter { it.grade == grade }
}



