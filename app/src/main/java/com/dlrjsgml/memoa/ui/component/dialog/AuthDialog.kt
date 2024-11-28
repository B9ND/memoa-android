package com.dlrjsgml.memoa.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dlrjsgml.memoa.ui.theme.Purple60

@Composable
fun dialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    buttonText: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9F)
                .height(150.dp)
                .clickable {
                    onDismiss()  // 클릭 시 onDismiss 함수 실행
                }
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        onDismiss()
                    }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(modifier.height(30.dp))
                Spacer(modifier.height(2.dp).fillMaxWidth().background(color = Color(0xFFD9D9D9)))
                Text(
                    text = buttonText,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 15.dp)
                        .clickable {
                            onDismiss()
                        },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Purple60
                )
            }
        }
    }
}




@Composable
@Preview
fun dialogPreview() {
    dialog(
        onDismiss = {},
        text = "로그인에 실패했습니다ddddd\nddddddddd",
        buttonText = "확인"
    )
}