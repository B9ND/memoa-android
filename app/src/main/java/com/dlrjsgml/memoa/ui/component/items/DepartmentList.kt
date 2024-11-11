package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DepartmentList(
    modifier: Modifier = Modifier,
    text: String,
    top: Boolean,
    bottom: Boolean,
    selected: Boolean
) {
    Box(
        modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(
                color = if(!selected) Color.White else Color(0xFFF5F5F5),
                shape = if(top && bottom) RoundedCornerShape(12.dp) else if(top) RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp) else if(bottom) RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp) else RoundedCornerShape(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}

@Composable
@Preview
fun DepartmentListPreview() {
    DepartmentList(
        text = "소프트웨어 개발과",
        top = true,
        bottom = false,
        selected = false
    )
}