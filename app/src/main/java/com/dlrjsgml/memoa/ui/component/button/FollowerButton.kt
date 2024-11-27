package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@Composable
fun FollowerButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {onClick() }
            )
            .background(color = if (!enabled) Purple60 else Color.White, shape = shape)
            .border(width = if(!enabled) 0.dp else 2.dp, color = Gray60, shape = shape)
            .width(105.dp)
    )

    {
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 13.dp).align(Alignment.Center),
            style = miniCaption1.copy(fontSize = 13.sp),
            text = if(!enabled) "팔로우" else "언팔로우",
            color = if (!enabled) Color.White else Gray60
        )
    }
}
@Composable
fun FollowerListButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    onClick: () -> Unit,
) {
    val tempState =  remember { mutableStateOf(enabled) }
    Box(
        modifier = modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {onClick()
                tempState.value = !tempState.value}
            )
            .background(color = if (!tempState.value) Purple60 else Color.White, shape = shape)
            .border(width = if(!tempState.value) 0.dp else 2.dp, color = Gray60, shape = shape)
            .width(105.dp)
    )

    {
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 13.dp).align(Alignment.Center),
            style = miniCaption1.copy(fontSize = 13.sp),
            text = if(!tempState.value) "팔로우" else "언팔로우",
            color = if (!tempState.value) Color.White else Gray60
        )
    }
}

@Preview
@Composable
private fun afdjkfdkaj() {
    FollowerButton() {

    }
}