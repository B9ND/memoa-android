package com.dlrjsgml.memoa.feature.main.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.button.MemoaButton
import com.dlrjsgml.memoa.ui.component.button.MemoaImageButton
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.CircleProfile
import com.dlrjsgml.memoa.ui.component.items.FollowNumber
import com.dlrjsgml.memoa.ui.component.textfield.ChangeEditText
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.caption2
import com.dlrjsgml.memoa.ui.theme.miniCaption1
import com.dlrjsgml.memoa.ui.theme.miniCaption2

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    navController: NavHostController,
) {
    val text = remember { mutableStateOf("이건희") }
    BackHandlers(navController = navController)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple60)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 12.dp)
                        .noRippleClickable {
                            navController.navigate(NavGroup.SETTING)
                        },
                    painter = painterResource(id = R.drawable.ic_setting),
                    contentDescription = null
                )
            }
        }

        item {

            Box(
                modifier = Modifier
                    .padding(top = 120.dp)
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
                    profile = "https://i.namu.wiki/i/wiP-b4EAaFe8dcrYKxfRSBSBzqOVI_CMPyTPj5UdQpKQyvM_Q3tamuTnofFGNGoaeMBYyn_cUoI2dXqX3jxlkg.webp"
                )

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
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        FollowNumber(number = 211, text = "팔로우")
                        Spacer(modifier = Modifier.width(35.dp))
                        FollowNumber(number = 211, text = "팔로잉")
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

        items(6) {
            Box(modifier = Modifier.background(Color.White)) {
                ArticleList(
                    profile = "https://i.namu.wiki/i/slmFMXb1Fchs2zN0ZGOzqfuPDvhRS-H9eBp7Gp613-DNKi6i6Ct7eFkTUpauqv5HAYR97mrNqrvvcCDEyBdL_g.webp"
                )
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun afjkldadlkjfsdakljf() {
//    ProfileScreen()
}

