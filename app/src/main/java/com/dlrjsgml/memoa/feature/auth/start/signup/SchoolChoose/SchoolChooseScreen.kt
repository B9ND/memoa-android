package com.dlrjsgml.memoa.feature.auth.start.signup.SchoolChoose

import android.os.Build
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.button.SchoolButton
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField
import com.dlrjsgml.memoa.ui.component.textfield.SearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchoolChooseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SchoolChooseScreenViewModel = viewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val isLaidOut by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedGrade by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            scope.launch {
                sheetState.show()
            }
        } else {
            scope.launch {
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
                fontWeight = FontWeight.Bold
            )
        ) {
            append("소속학교")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp
            )
        ) {
            append("를 선택하세요")
        }
    }

    LaunchedEffect(key1 = isLaidOut) {
        if (isLaidOut) {
            focusRequester.requestFocus()
        }
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
                .padding(horizontal = 20.dp, vertical = 45.dp)
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
                    fontSize = 30.sp,
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
                            isSelected = selectedGrade == "1학년"
                        ) { isSelected ->
                            if (isSelected) selectedGrade = "1학년"
                        }
                        Spacer(Modifier.width(10.dp))
                        SchoolButton(
                            text = "2학년",
                            isSelected = selectedGrade == "2학년"
                        ) { isSelected ->
                            if (isSelected) selectedGrade = "2학년"
                        }
                        Spacer(Modifier.width(10.dp))
                        SchoolButton(
                            text = "3학년",
                            isSelected = selectedGrade == "3학년"
                        ) { isSelected ->
                            if (isSelected) selectedGrade = "3학년"
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    MemoaTextField(
                        firstFocus = false,
                        value = uiState.school,
                        onValueChange = viewModel::updateSchool,
                        hint = schoolText,
                        modifier = Modifier.clickable (
                            onClick = {
                                showBottomSheet = true
                            }
                        )
                    )
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
                Spacer(Modifier.height(10.dp))
                MemoaButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    text = "다음",
                    enabled = true,
                ) {
                    navController.navigate(NavGroup.SIGNUP_SCHOOL_NOT_FOUND)
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                ) {
                    SearchTextField(
                        value = uiState.school,
                        onValueChange = viewModel::updateSchool,
                        hint = schoolText.toString(),
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
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

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview
//fun SchoolChooseScreenPreView() {
//    SchoolChooseScreen()
//}