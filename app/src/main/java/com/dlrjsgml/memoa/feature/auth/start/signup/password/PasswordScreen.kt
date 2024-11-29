package com.dlrjsgml.memoa.feature.auth.start.signup.password

import android.os.Build
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.textfield.AuthText
import com.dlrjsgml.memoa.ui.component.textfield.MemoaPasswordTextField

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PasswordScreenViewModel = viewModel(),
    email: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val textFieldHasFocus = remember { mutableStateOf(false) }
    val passwordText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("비밀번호")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp
            )
        ) {
            append("를 입력하세요")
        }
    }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
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
                Spacer(Modifier.height(50.dp))
                MemoaPasswordTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    hint = passwordText,
                    modifier = Modifier.focusRequester(focusRequester).padding(horizontal = 10.dp).onFocusChanged { focusState -> textFieldHasFocus.value = focusState.isFocused },
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (uiState.password.length > 255) "비밀번호 길이 초과입니다." else "",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

            }
            Column(
                modifier
                    .align(alignment = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (!textFieldHasFocus.value) {
                    AuthText()
                }
                Spacer(Modifier.height(5.dp))
                MemoaButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    text = "다음",
                    enabled = true,
                ) {
                    if (uiState.password.length <= 255 && uiState.password.isNotEmpty()) {
                        navController.navigate("${NavGroup.SIGNUP_NICKNAME}?${email}?${uiState.password}")
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun EmailScreenPreview() {
    PasswordScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        email = "good"
    )
}