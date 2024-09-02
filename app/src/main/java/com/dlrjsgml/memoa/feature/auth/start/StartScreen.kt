package com.dlrjsgml.memoa.feature.auth.start

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.MemoaButton
import com.dlrjsgml.memoa.ui.theme.Purple0
import com.dlrjsgml.memoa.ui.theme.Purple10
import com.dlrjsgml.memoa.ui.theme.caption1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Purple0, Purple10)))
    )
    Column {
        Column(modifier = Modifier.padding(top = 70.dp, start = 40.dp)) {
            Text(text = "우리 학교 필기 공유", style = caption1.copy(fontSize = 16.sp), color = Color.White)
            Text(
                modifier = Modifier.padding(start = 0.dp),
                text = "MEMOA",
                style = caption1.copy(fontSize = 35.sp),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.padding(horizontal = 35.dp, vertical = 10.dp)) {
            MemoaButton(modifier = Modifier.fillMaxWidth(),
                enabled = true,
                contentPadding = PaddingValues(vertical = 18.dp),
                text = "로그인",
                onClick = {
                    navController.navigate(NavGroup.LOGIN)
                })
        }
        Box(modifier = Modifier.padding(horizontal = 35.dp).padding(bottom = 40.dp)) {
            MemoaButton(modifier = Modifier.fillMaxWidth(),
                enabled = false,
                contentPadding = PaddingValues(vertical = 18.dp),
                text = "회원가입",
                onClick = {
                    navController.navigate(NavGroup.SIGNUP_EMAIL)

                })
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview
//fun fadjadfj() {
//    StartScreen()
//}