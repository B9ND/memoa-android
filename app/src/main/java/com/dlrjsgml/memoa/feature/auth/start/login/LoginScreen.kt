package com.dlrjsgml.memoa.feature.auth.start.login

import android.app.Dialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.feature.auth.start.signup.email.Code
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButtonWhite
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.dialog.dialog
import com.dlrjsgml.memoa.ui.component.textfield.MemoaPasswordTextField
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            Log.d("alert쪽", "LoginScreen: ${effect}")
            when (effect) {
                LoginSideEffect.Success -> {
                    viewModel.saveTokens(context)
                    navController.navigate(NavGroup.MAIN)
                    Log.d("alert쪽", "LoginScreen: success")
                }

                LoginSideEffect.Failed -> {
                    Log.d("alert쪽", "LoginScreen: fail")
                    viewModel.updateDialog(true)
                    Log.d("뷰모델쪽", "LoginScreen: ${uiState.showDialog}")
                }
            }
        }
    }

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

    if (uiState.showDialog) {
        Box(
            modifier.clickable{
                viewModel.updateDialog(false)
            }
        ) {
            dialog(
                onDismiss = {
                    viewModel.clearError()
                    viewModel.updateDialog(false)
                },
                text = "로그인에 실패했습니다",
                buttonText = "확인",
                modifier = modifier.clickable {
                    viewModel.clearError()
                    Log.d("나니?", "LoginScreen: ${uiState.showDialog}")
                    viewModel.updateDialog(false)
                    Log.d("나니?", "LoginScreen: ${uiState.showDialog}")
                }
            )
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
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .focusRequester(focusRequester),
                )
                Spacer(Modifier.height(10.dp))
                MemoaPasswordTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    hint = authText,
                )
                Spacer(Modifier.height(10.dp))
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
                    onClick = {
                        viewModel.login(uiState.email, uiState.password)
                        Log.d("테스트", "LoginScreen: ${uiState.error}")
                        Log.d("테스트", "LoginScreen: ${uiState.error}")
                    }
                )
            }
        }
    }
}



