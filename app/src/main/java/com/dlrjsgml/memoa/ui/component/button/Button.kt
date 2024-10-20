package com.dlrjsgml.memoa.ui.component.button


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow
import com.dlrjsgml.memoa.ui.theme.ButtonColor
import com.dlrjsgml.memoa.ui.theme.caption1


@RequiresApi(Build.VERSION_CODES.O)
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
        modifier = Modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = onClick
            )
            .drawColoredShadow(
                Color.Black
            )
    ) {
        Box(
            modifier = modifier.background(
                color = if (enabled) ButtonColor else Color.White, shape = shape
            )

        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(contentPadding),
                text = text,
                color = if (enabled) Color.Black else Color.Gray, //색깔 꼭 바꾸자 ㅎㅎ
                style = caption1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )

        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun MemoaButtonPreview() {
    MemoaButton(
        text = "다음",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        enabled = false
    ) {}
}

