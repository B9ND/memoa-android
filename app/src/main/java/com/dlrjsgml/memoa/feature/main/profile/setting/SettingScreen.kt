package com.dlrjsgml.memoa.feature.main.profile.setting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.ShadowButton
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.CircleProfile
import com.dlrjsgml.memoa.ui.component.items.FollowNumber
import com.dlrjsgml.memoa.ui.component.textfield.ChangeEditText
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen(
//    navController: NavHostController,
) {
    val text = remember { mutableStateOf("이건희") }
    val scrollState = rememberScrollState()
//    BackHandlers(navController = navController)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Gray30)
        ) {
        Box(
            modifier = Modifier
                .padding(top = 167.dp)
                .fillMaxWidth()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            CircleProfile(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -60.dp),
                profile = "https://newsimg.hankookilbo.com/cms/articlerelease/2021/04/26/813324fb-5b9a-4065-a064-cb52e7c21156.jpg"
            )

            Box(modifier = Modifier.background(color = Color.White)){

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)

            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    ChangeEditText(
                        value = text.value,
                        onValueChange = { text.value = it },
                        hint = "gddg"
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "leegeh1213@gmail.com",
                    style = miniCaption1
                )
                Spacer(modifier = Modifier.height(15.dp))

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        ) {

            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "소속 변경") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "개인정보 이용 약관") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "로그아웃") {

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun fdjafdj(){
    SettingScreen()
}