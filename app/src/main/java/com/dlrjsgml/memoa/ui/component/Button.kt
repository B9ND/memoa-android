package com.dlrjsgml.memoa.ui.component


import android.graphics.BlurMaskFilter
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.animation.bounceClick
import com.dlrjsgml.memoa.ui.theme.caption1
import com.dlrjsgml.memoa.ui.theme.caption2


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
            .bounceClick(
                onClick = onClick
            )
            .dropShadow(
                shape = shape,
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

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.12f),
    blur: Dp = 14.dp,
    offsetY: Dp = 10.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}