package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@Composable
fun FollowNumber(
    modifier: Modifier = Modifier,
    number: Int,
    text: String,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier.clickable(
        indication = rememberBounceIndication(
            scale = 0.95f,
            showBackground = true,
            radius = RoundedCornerShape(8.dp)
        ),
        interactionSource = remember { MutableInteractionSource() },
        onClick = {onClick() }

    )) {
        Text(
            modifier = modifier.align(Alignment.CenterHorizontally),
            text = number.toString(),
            style = boardName
        )
        Text(
            modifier = modifier.align(Alignment.CenterHorizontally),
            text = text,
            style = miniCaption1
        )
    }
}

@Preview
@Composable
private fun afjdk() {
    FollowNumber(number = 123, text = "팔로워")

}