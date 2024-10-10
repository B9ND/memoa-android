package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun SearchHistoryList(content: String, shape: Shape = RoundedCornerShape(40)) {
    Box(modifier = Modifier
        .padding(end = 8.dp, bottom = 8.dp)
        .background(Color.White, shape)
        .border(width = 2.dp, color = Gray30, shape = shape)
        ){
        Text(modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 24.dp, vertical = 8.dp), text = content, style = caption1Regular.copy(fontSize = 16.sp))

    }
}

@Preview
@Composable
private fun afjdadjkdjafk(){
    SearchHistoryList(content = "여승원 농구하다")
}