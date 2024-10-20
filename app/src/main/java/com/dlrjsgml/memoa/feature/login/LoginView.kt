package com.dlrjsgml.memoa.feature.login

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.feature.SignUp.email.addFocusCleaner
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val emailText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("이메일")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp
            )
        ) {
            append("를 입력하세요")
        }
    }
    val authText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("인증번호 6자리")
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
                    text = "로그인",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(30.dp))
                MemoaTextField(
                    value = uiState.email,
                    onValueChange = viewModel::updateEmail,
                    hint = emailText,
                    firstFocus = true,
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Spacer(Modifier.height(10.dp))
                MemoaTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    hint = authText,
                    firstFocus = false,
                )
            }
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                MemoaButton(
                    modifier = modifier
                        .align(alignment = Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(55.dp),
                    text = "로그인",
                    enabled = true,
                ) {
                    navController.navigate(NavGroup.MAIN)
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

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview
//fun EmailScreenPreview() {
//    LoginScreen()
//}