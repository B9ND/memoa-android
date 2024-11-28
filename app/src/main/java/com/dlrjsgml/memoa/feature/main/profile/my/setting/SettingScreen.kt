package com.dlrjsgml.memoa.feature.main.profile.my.setting

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.backhandler.safePopBackStack
import com.dlrjsgml.memoa.feature.main.profile.my.MyProfileEffect
import com.dlrjsgml.memoa.feature.main.profile.my.ProfileViewModel
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.network.write.image.getFileName
import com.dlrjsgml.memoa.network.write.image.uriToBitmap
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.ShadowButton
import com.dlrjsgml.memoa.ui.component.dialog.MemoaSimpleDialog
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.items.CircleProfile
import com.dlrjsgml.memoa.ui.component.items.FollowNumber
import com.dlrjsgml.memoa.ui.component.textfield.ChangeEditText
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.caption1
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.caption2
import com.dlrjsgml.memoa.ui.theme.miniCaption1
import com.dlrjsgml.memoa.ui.theme.miniCaption2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(),
    writeViewModel: WriteViewModel = viewModel(),
    settingViewModel: SettingViewModel = viewModel(),
) {
    val text = remember { mutableStateOf("이건희") }
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    val isShowDialog = remember { mutableStateOf(false) }
    if (isShowDialog.value) {
        MemoaSimpleDialog(
            content = "준비중인 기능입니다.",
            onClickConfirm = { isShowDialog.value = false }
        )
    }
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 14.sp,
                color = Color.Red,
                textDecoration = TextDecoration.Underline // 밑줄 추가
            )
        ) {
            append("회원탈퇴")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                Log.d("글쓰기", "ChatDetailScreen: $uri")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    selectedImageBitmap = context.contentResolver.uriToBitmap(uri)
                }
                selectedFileName = context.contentResolver.getFileName(uri).toString()
                Log.d("글쓰기", "ChatDetailScreen: $selectedFileName $selectedImageBitmap")
                viewModel.changeProfileImage(uri, context, selectedImageBitmap!!)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 권한 확인
    val permissionCheckResult = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    LaunchedEffect(Unit) {
        viewModel.getProfileInfo()
    }
    LaunchedEffect(settingViewModel) {
        settingViewModel.uiEffect.collect { effect ->
            when (effect) {
                SettingSideEffect.LogoutFailed -> {}
                SettingSideEffect.LogoutSuccess -> {
                    navController.navigate(NavGroup.START) {
                        popUpTo(0) { inclusive = true } // 모든 백 스택 제거
                        launchSingleTop = true         // 동일한 목적지가 여러 번 쌓이는 것 방지
                    }
                }
            }
        }
    }
//    BackHandlers(navController = navController)
    Column(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val height = size.height
            val purpleHeight = height / 3

            // 보라색 영역
            drawRect(
                color = Gray30,
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
        .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            BackButton {
                navController.safePopBackStack()
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable(
                    indication = rememberBounceIndication(
                        scale = 0.95f,
                        showBackground = true,
                        radius = RoundedCornerShape(8.dp)
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = true,
                    onClick = {
                        navController.safePopBackStack()
                    }
                ),
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
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -60.dp)
                    .clickable(
                        indication = rememberBounceIndication(
                            scale = 0.95f,
                            showBackground = true,
                            radius = RoundedCornerShape(8.dp)
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = true,
                        onClick = {
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                galleryLauncher.launch("image/*")
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                }
                            }
                        }
                    )
            ) {
                CircleProfile(
                    modifier = Modifier,
                    profile = uiState.profileImage
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    painter = painterResource(R.drawable.ic_change_image),
                    contentDescription = null
                )
            }


            Box(modifier = Modifier.background(color = Color.White)) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp), text = "이름 변경") {
                navController.navigate(NavGroup.NAME_SETTING)
            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp), text = "자기소개 변경") {
                navController.navigate(NavGroup.DESCRIPTION_SETTING)
            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp), text = "개인정보 이용 약관") {

            }
            Spacer(modifier = Modifier.height(12.dp))
            ShadowButton(modifier = Modifier.padding(horizontal = 20.dp), text = "로그아웃") {
                settingViewModel.logout(context = context)
            }
            Text(
                modifier = Modifier
                    .clickable(
                        indication = rememberBounceIndication(
                            scale = 0.95f,
                            showBackground = true,
                            radius = RoundedCornerShape(8.dp)
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = true,
                        onClick = {
                            isShowDialog.value = true
                        }
                    )
                    .align(Alignment.End)
                    .padding(end = 36.dp, top = 8.dp),

                text =annotatedText, style = miniCaption1.copy(fontSize = 14.sp, color = Color.Red),
            )
            Spacer(modifier = Modifier.height(220.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun fdjafdj() {
    SettingScreen(navController = rememberNavController())
}