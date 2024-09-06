package com.dlrjsgml.memoa.feature.auth.start

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.component.MemoaButton
import com.dlrjsgml.memoa.ui.component.textfield.MemoaTextField
import com.dlrjsgml.memoa.ui.theme.Purple0
import com.dlrjsgml.memoa.ui.theme.Purple10

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUp_nameScreen(modifier: Modifier = Modifier) {
    val (text, setValue) = remember {
        mutableStateOf("")
    }
    Box(
        modifier
            .background(brush = Brush.verticalGradient(listOf(Purple0, Purple10)))
            .fillMaxSize()
    ) {
        Box(
            modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(30.dp)
                .padding(top = 40.dp)
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 5.dp)
                    ,
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null
                )
                Text(
                    "뒤로가기",
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            Box(
                modifier
                    .padding(top = 40.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    "회원가입",
                    fontWeight = Bold,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = 10.dp)
                )
                Box(
                    modifier
                        .padding(top = 80.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Box(modifier = Modifier.height(30.dp))
                        MemoaTextField(
                            value = text,
                            onValueChange = setValue,
                            hint = "닉네임을 입력하세요"
                        )
                    }
                }
            }
        }
        Box(
            modifier
                .align(Alignment.BottomCenter)
                .offset(y = 300.dp),
            contentAlignment = Alignment.BottomCenter,


            ) {
            Image(
                painter = painterResource(R.drawable.goorm),
                contentDescription = null,
                modifier
                    .size(1024.dp)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 55.dp)
                .padding(horizontal = 20.dp)
        ) {
            MemoaButton(
                text = "다음",
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                enabled = true
            ) { }
        }
        val text1 = stringResource(R.string.개인정보)
        val text2 = stringResource(R.string.이용약관)

        val styledText = buildAnnotatedString {
            append("계정을 생성함으로써,\n")
            withStyle(style = SpanStyle(color = colorResource(R.color.text_blu))) { // 빨간색 적용
                append(text2)
            }
            append("과 ")
            withStyle(style = SpanStyle(color = colorResource(R.color.text_blu))) { // 빨간색 적용
                append(text1)
            }
            append("에 동의하였음을 확인합니다.")
        }

        BasicText(
            text = styledText,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 130.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun TestPreview() {
    SignUp_nameScreen()
}