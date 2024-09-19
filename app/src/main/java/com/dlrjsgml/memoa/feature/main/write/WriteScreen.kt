package com.dlrjsgml.memoa.feature.main.write

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


@Composable
fun WriteScreen(
    viewModel: WriteViewModel = viewModel(),
) {
    val selectTags = arrayListOf("국어","영어","수학","사회","과학","기타")
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit) {

    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .width(12.dp)
                    .height(19.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            }
            Text(
                modifier = Modifier.padding(start = 9.dp),
                text = "뒤로가기",
                style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
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
        LazyRow(modifier = Modifier.padding(horizontal = 21.dp)) {
            items(selectTags.size) {
                MemoaCheckBox(
                    text = selectTags[it],
                    onClick = { viewModel.fillTags(selectTags[it]) } // Pass the single tag
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        SimpleTextField(
            hintColorWhite = true,
            modifier = Modifier.padding(horizontal = 21.dp),
            singleLine = false,
            minLines = 9,
            maxLines = 9,
            value = uiState.content,
            onValueChange = viewModel::updateContent,
            hint = "내용을 입력하세요"
        )
    }

}

@Preview
@Composable
private fun afdjadfj() {
    WriteScreen()
}