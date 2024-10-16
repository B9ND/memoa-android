package com.dlrjsgml.memoa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun MemoaCheckBox(
    text: String,
    shape: Shape = RoundedCornerShape(12.dp),
    onClick: () -> Unit,
) {
    val checked = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(color = Color.White, shape = shape)
            .border(width = (1.6.dp), color = if (checked.value) Purple60 else Gray30, shape = shape)
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {
                onClick()
                checked.value = !checked.value
            })
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Text(text = text, style = caption1Regular)
    }
}

@Preview
@Composable
private fun afdjadfj() {
    MemoaCheckBox(text = "국어", onClick = {})

}