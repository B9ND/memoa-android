package com.dlrjsgml.memoa.feature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.component.MemoaButton
import com.dlrjsgml.memoa.ui.theme.Gray20
import com.dlrjsgml.memoa.ui.theme.Purple0
import com.dlrjsgml.memoa.ui.theme.Purple10
import com.dlrjsgml.memoa.ui.theme.caption1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TestScreen() {

    var text by remember { mutableStateOf("") }


    Box(
        modifier = Modifier.background(brush = Brush.verticalGradient(listOf(Purple0, Purple10)))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(280.dp)
                .background(
                    color = Gray20,
                    shape = RoundedCornerShape(topStart = 120.dp, topEnd = 600.dp)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(200.dp)
                .background(color = Gray20, shape = RoundedCornerShape(topStart = 220.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 150.dp, bottom = 150.dp)
                .align(Alignment.BottomCenter)
                .height(200.dp)
                .background(color = Gray20, shape = RoundedCornerShape(topStart = 220.dp))
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(500.dp)
                .padding(bottom = 60.dp),
            painter = painterResource(id = R.drawable.ic_home_welcome),
            contentDescription = null
        )


        Column(
            modifier = Modifier
        ) {
            Column(modifier = Modifier.padding(top = 50.dp, start = 25.dp)) {
                Text(
                    text = "우리 학교 필기 공유", style = caption1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ), color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 1.dp),
                    text = "MEMOA", style = caption1.copy(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.SemiBold
                    ), color = Color.White
                )
            }


            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp)) {
                MemoaButton(modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    contentPadding = PaddingValues(vertical = 18.dp),
                    text = "로그인",
                    onClick = {})
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(bottom = 40.dp)
            ) {
                MemoaButton(modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    contentPadding = PaddingValues(vertical = 18.dp),
                    text = "시작하기",
                    onClick = {})
            }

        }
    }

}

@Preview
@Composable
fun afdjafjd() {
    TestScreen()
}