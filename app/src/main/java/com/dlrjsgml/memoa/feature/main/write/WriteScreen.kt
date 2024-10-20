package com.dlrjsgml.memoa.feature.main.write

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.component.MemoaCheckBox
import com.dlrjsgml.memoa.ui.component.textfield.SimpleTextField
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.backhandler.BackHandlers
import com.dlrjsgml.memoa.network.write.image.getFileName
import com.dlrjsgml.memoa.network.write.image.uriToBitmap
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.dialog.MemoaSimpleDialog
import com.dlrjsgml.memoa.ui.component.items.ArticleImage
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun WriteScreen(
    viewModel: WriteViewModel = viewModel(),
    navController: NavHostController
) {
    val selectTags = arrayListOf("국어", "영어", "수학", "사회", "과학", "기타")
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val customAlertDialogState = viewModel.customAlertDialogState.value
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                Log.d("글쓰기", "ChatDetailScreen: $uri")
                selectedImageBitmap = context.contentResolver.uriToBitmap(uri)
                selectedFileName = context.contentResolver.getFileName(uri).toString()
                Log.d("글쓰기", "ChatDetailScreen: $selectedFileName $selectedImageBitmap")
                viewModel.uploadImage(uri,context)
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect{ effect->
            when(effect){
                is WriteSideEffect.Success -> {
                    navController.popBackStack()
                    Log.d("글쓰기", "성공");

                }
                is WriteSideEffect.Failure ->{
                    viewModel.wrigingErrorAlert()
                    Log.d("글쓰기", "실패");
                }
            }

        }
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BackHandlers(navController = navController)
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
                    if(uiState.title.isNotBlank() && uiState.content.isNotBlank()){
                        viewModel.postWrite()
                    }else{
                        viewModel.plsAllWrite()
                    }
                    },
                text = "완료",
                color = Purple60,
                style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        SimpleTextField(
            modifier = Modifier
                .padding(horizontal = 21.dp)
                .padding(top = 23.dp),
            value = uiState.title,
            onValueChange = viewModel::updateTitle,
            hint = "제목을 입력하세요"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box{
            LazyRow(modifier = Modifier
                .padding(horizontal = 21.dp)
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Horizontal
                )) {
                items(selectTags.size) {
                    MemoaCheckBox(
                        text = selectTags[it],
                        onClick = { viewModel.fillTags(selectTags[it]) } // Pass the single tag
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            val contentText = remember {
                mutableStateOf("")
            }
            SimpleTextField(
                hintColorWhite = true,
                modifier = Modifier.padding(horizontal = 21.dp),
                singleLine = false,
                minLines = 14,
                maxLines = 14,
                value = uiState.content,
                onValueChange = viewModel::updateContent,
                hint = "내용을 입력하세요"
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp),
                onClick = {
                    galleryLauncher.launch("image/*")})
            {
                Image(painter = painterResource(id = R.drawable.ic_get_gallery), contentDescription = null)
            }
//            selectedImageUri?.let {
//                Log.d("ㅎㅇ", selectedImageUri.toString());
//                Image(
//                    painter = rememberImagePainter(it),
//                    contentDescription = null,
//                    modifier = Modifier.size(200.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
        }
        LazyRow {
            items(uiState.image.size){
                ArticleImage(image = uiState.image[it])
            }
        }
        Spacer(modifier = Modifier.height(200.dp))
        if (customAlertDialogState.content.isNotBlank()) {
            MemoaSimpleDialog(
                content = customAlertDialogState.content,
                onClickConfirm = { customAlertDialogState.onClickConfirm() }
            )
        }
    }
}

@Preview
@Composable
private fun afdjadfj() {
//    WriteScreen()
}