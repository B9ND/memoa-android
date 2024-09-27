package com.dlrjsgml.memoa.ui.component.button


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MemoaImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: Painter

) {
    Image(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(
                    color = Color.Gray, // Ripple 색상
                    bounded = false, // 사각형 모양의 Ripple
                    radius = 18.dp // Ripple 크기
                ),
                onClick = {
                    onClick()
                }
            )
            .padding(9.dp),
        painter = content, contentDescription = null)
}