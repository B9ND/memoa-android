package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SchoolList(
    modifier: Modifier = Modifier,
    schoolName: String,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.White)
            .padding(start = 15.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = schoolName,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
    Spacer(Modifier.height(1.dp).fillMaxWidth().background(Color(0xFFF0F0F0)))
}

@Composable
@Preview
fun SchoolListPreView() {
    SchoolList(
        schoolName = "건국초등학교"
    )
}
