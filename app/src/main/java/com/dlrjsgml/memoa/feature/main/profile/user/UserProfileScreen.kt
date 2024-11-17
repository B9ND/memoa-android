package com.dlrjsgml.memoa.feature.main.profile.user

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.feature.main.profile.my.MyProfileEffect
import com.dlrjsgml.memoa.feature.main.profile.my.ProfileViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.FollowerButton
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.CircleProfile
import com.dlrjsgml.memoa.ui.component.items.FollowNumber
import com.dlrjsgml.memoa.ui.component.textfield.ChangeEditText
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.miniCaption1
import kotlinx.collections.immutable.toImmutableList

@Composable
fun UserProfileScreen(
    userName: String,
    navController: NavHostController,
    viewModel: UserProfileViewModel = viewModel(),
) {
    val text = remember { mutableStateOf("이건희") }
    val uiState by viewModel.uiState.collectAsState()
    val followUiState by viewModel.followUiState.collectAsState()
    Log.d("성공", "dlrjsgml44 Ok ${uiState.followed}");
    LaunchedEffect(Unit) {
        viewModel.getUserProfileInfo(userName)
    }
    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                UserProfileEffect.Failed -> {
                    Log.d("프로필", "에러");
                }

                UserProfileEffect.Success -> {
                    viewModel.getUsersArticles(uiState.nickname)
                    viewModel.getFollowSize()

                }
            }
        }
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val height = size.height
                    val purpleHeight = height / 3

                    // 보라색 영역
                    drawRect(
                        color = Purple60,
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, purpleHeight)
                    )

                    // 하얀색 영역
                    drawRect(
                        color = Color.White,
                        topLeft = Offset(0f, purpleHeight),
                        size = Size(size.width, height - purpleHeight)
                    )
                }
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 46.dp, end = 12.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(top = 98.dp)
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                ) {
                    CircleProfile(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = -60.dp)
                            .noRippleClickable {
                                navController.navigate("${NavGroup.IMAGE_DETAIL}?${uiState.profileImage}")
                            },
                        profile = uiState.profileImage
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                    ) {
                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(text = uiState.nickname, style = boardName)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = uiState.email,
                            style = miniCaption1
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            FollowNumber(
                                number = followUiState.follower,
                                text = "팔로우",
                                onClick = { navController.navigate("${NavGroup.FOLLOWER}?${uiState.nickname}?true") })
                            Spacer(modifier = Modifier.width(35.dp))
                            FollowNumber(
                                number = followUiState.following,
                                text = "팔로잉",
                                onClick = { navController.navigate("${NavGroup.FOLLOWER}?${uiState.nickname}?false") })
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        FollowerButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            enabled = uiState.followed,
                            onClick = {
                                viewModel.follow(uiState.nickname)
                            })
                        Spacer(modifier = Modifier.height(25.dp))

                    }
                }
            }

            items(uiState.articles.size) {
                val article = uiState.articles[it]
                Box(modifier = Modifier.background(Color.White)) {
                    ArticleList(
                        id = article.id,
                        name = article.author,
                        date = article.createdAt,
                        title = article.title,
                        image = article.images.toImmutableList(),
                        profile = article.authorProfileImage,
                        tag = article.tags.toImmutableList(),
                        comment = 1,
                        onProfileClick = { navController.navigate("${NavGroup.USERPROFILE}?${article.author}") },
                        onBookmarkClick = {
//                        viewModel.bookmark(article.id)
                        },
                        onCommentClick = {},
                        onArticleClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") },
                        onImageClick = { navController.navigate("${NavGroup.DETAIL}?${article.id}") }
                    )
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun afjkldadlkjfsdakljf() {
    val navHostController = rememberNavController()

    UserProfileScreen(
        userName = "이건희",
        navController = navHostController,
    )
}

