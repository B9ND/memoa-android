package com.dlrjsgml.memoa.feature.main.profile.my.setting

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.profile.my.ProfileViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.ShadowButton
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.CircleProfile
import com.dlrjsgml.memoa.ui.component.items.FollowNumber
import com.dlrjsgml.memoa.ui.component.textfield.ChangeEditText
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val text = remember { mutableStateOf("이건희") }
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProfileInfo()
    }
//    BackHandlers(navController = navController)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Gray30)
        .verticalScroll(scrollState)
        ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            BackButton {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.noRippleClickable {
                },
                text = "완료",
                color = Purple60,
                style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 87.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
            
        ) {
            CircleProfile(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -60.dp),
                profile = uiState.profileImage
            )

            Box(modifier = Modifier.background(color = Color.White)){

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)

            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = uiState.nickname, style = boardName)
//                    ChangeEditText(
//                        value = text.value,
//                        onValueChange = { text.value = it },
//                        hint = "gddg"
//                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = uiState.email,
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

            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "이름 변경") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "자기소개 변경") {

            }
            Spacer(modifier = Modifier.height(12.dp))

            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "소속 변경") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "개인정보 이용 약관") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp),text = "로그아웃") {

            }
            Spacer(modifier = Modifier.height(220.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun fdjafdj(){
    SettingScreen(navController = rememberNavController())
}