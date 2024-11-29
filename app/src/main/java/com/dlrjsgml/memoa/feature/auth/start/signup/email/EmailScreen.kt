package com.dlrjsgml.memoa.feature.auth.start.signup.email

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
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
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.remote.NetworkUtil
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.dialog.dialog
import com.dlrjsgml.memoa.ui.component.textfield.AuthText
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmailScreen(
    modifier: Modifier = Modifier,
    viewModel: EmailViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val textFieldHasFocus = remember { mutableStateOf(false) }

    val emailText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("이메일")
        }
        withStyle(
            SpanStyle(
                fontSize = 14.sp,
            )
        ) {
            append("를 입력하세요")
        }
    }
    val authText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("인증번호 6자리")
        }
        withStyle(
            SpanStyle(
                fontSize = 14.sp
            )
        ) {
            append("를 입력하세요")
        }
    }

    // Request focus when the screen is displayed
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                Code.Success -> {
                    navController.navigate("${NavGroup.SIGNUP_PASSWORD}?${uiState.email}")
                }
                Code.Fail -> {
                    viewModel.updateShowDialog(true)
                }
            }
        }
    }
    LaunchedEffect(uiState.errorCode) {
        if (uiState.errorCode == 500 || uiState.errorCode == 409 || uiState.errorCode == 400) {
            viewModel.updateClicked(false)
        }
    }
    LaunchedEffect(uiState.clicked) {
        if (uiState.clicked) {
            while (uiState.time >= 1) {
                delay(1.seconds)
                viewModel.delTime(uiState.time)
            }
            viewModel.updateClicked(false)
            viewModel.updateTime(300)
            Log.d("나인", "EmailScreen: ${uiState.time}")
        }
    }
    if (uiState.loadingState) {
        LaunchedEffect(Boolean) {
            delay(1500)
            viewModel.updateLoadingState(false)
            Toast.makeText(MemoaApplication.getContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            viewModel.updateClicked(false)
            viewModel.updateTime(300)
        }
    }
    fun updateEmail(newInput: String) {
        viewModel.updateEmail(newInput)
        viewModel.updateClicked(false)
        viewModel.updateTime(300)
    }
    if (uiState.showDialog) {
        dialog(
            onDismiss = { viewModel.updateShowDialog(false) },
            text = uiState.error,
            buttonText = "확인"
        )
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
                Spacer(Modifier.height(30.dp))
                MemoaTextField(
                    value = uiState.email,
                    onValueChange = { newValue -> updateEmail(newValue) },
                    hint = emailText,
                    textButton = true,
                    textButtonVal = if (uiState.clicked) uiState.time.toString() else "인증",
                    firstFocus = true,
                    modifier = Modifier.focusRequester(focusRequester).padding(horizontal = 10.dp).onFocusChanged { focusState -> textFieldHasFocus.value = focusState.isFocused },
                    textButtonOnClick = {
                        if (uiState.email.isNotEmpty()) {
                            coroutineScope.launch {
                                viewModel.updateErrorCode(0)
                                viewModel.updateError("")
                                viewModel.updateClicked(true)
                                viewModel.sendCode(
                                    uiState.email,
                                    NetworkUtil(MemoaApplication.getContext())
                                )
                            }
                        } else {
                            viewModel.updateError("이메일을 입력해 주세요")
                        }
                    }
                )
                Spacer(Modifier.height(10.dp))
                MemoaTextField(
                    modifier.padding(horizontal = 10.dp).onFocusChanged { focusState ->
                        textFieldHasFocus.value = focusState.isFocused
                                                                        },
                    value = uiState.auth,
                    onValueChange = viewModel::updateAuth,
                    hint = authText,
                    firstFocus = false
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                if (!textFieldHasFocus.value) {
                    AuthText()
                }
                Spacer(modifier.height(5.dp))
                MemoaButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .imePadding(),
                    text = "다음",
                    enabled = true,
                    isLoading = uiState.loadingState
                ) {
                    viewModel.updateEmail(uiState.email)
                    focusManager.clearFocus()
                    Log.d("let's", "EmailScreen: ${uiState.errorCode}")
                    if (uiState.auth.length == 6) {
                        coroutineScope.launch {
                            try {
                                viewModel.checkCode(uiState.email, uiState.auth, NetworkUtil(MemoaApplication.getContext()))
                            } catch (e: Exception) {
                                Log.d("Auth Check", "Error during checkCode: ${e.message}")
                                e.printStackTrace()
                            }
                        }
                    } else {
                        if (uiState.errorCode == 100 || uiState.errorCode == 401){
                            viewModel.updateError("인증번호를 확인해 주세요")
                        } else {
                            viewModel.updateError("이메일 인증을 진행해 주세요")
                        }
                        Log.d("good", "EmailScreen: ${uiState.errorCode}")
                        viewModel.updateShowDialog(true)
                    }
                }
            }
        }
    }
}

fun Modifier.addFocusCleaner(
    focusManager: FocusManager,
    doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview
//fun EmailScreenPreview() {
//    EmailScreen()
//}