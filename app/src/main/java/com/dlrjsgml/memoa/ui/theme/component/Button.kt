package com.dlrjsgml.memoa.ui.theme.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.animation.bounceClick
import com.dlrjsgml.memoa.ui.theme.caption1


@Composable
fun MemoaButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.bounceClick(
            onClick = onClick
        )
    ) {
        Box(
            modifier = modifier.background(
                color = if (enabled) Purple20 else Color.White, shape = shape
            )
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(contentPadding),
                text = text,
                color = if (enabled) Color.Black else Color.Gray, //색깔 꼭 바꾸자 ㅎㅎ
                style = caption1.copy(
                    fontWeight = FontWeight.Normal
                )
            )

        }

    }
}


@Preview
@Composable
fun MemoaButtonPreview() {
    MemoaButton(
        text = "다음",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {}
}