package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow
import com.dlrjsgml.memoa.ui.theme.ButtonColor
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1
import com.dlrjsgml.memoa.ui.theme.miniCaption1
import com.dlrjsgml.memoa.ui.theme.miniCaption2

@Composable
fun FollowerButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    onClick: () -> Unit,
) {
    val isEnable = remember { mutableStateOf(enabled) }
    Box(
        modifier = modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {onClick()
                isEnable.value = !isEnable.value}
            )
            .background(color = if (isEnable.value) Purple60 else Color.White, shape = shape)
            .border(width = if(isEnable.value) 0.dp else 2.dp, color = Gray60, shape = shape)
    )

    {
        Text(
            modifier = modifier.padding(vertical = 6.dp, horizontal = 32.dp),
            style = miniCaption2,
            text = if(isEnable.value) "팔로우" else "언팔로우",
            color = if (isEnable.value) Color.White else Gray60
        )

    }
}

@Preview
@Composable
private fun afdjkfdkaj() {
    FollowerButton() {

    }
}