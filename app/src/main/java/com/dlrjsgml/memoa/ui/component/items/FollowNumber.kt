package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@Composable
fun FollowNumber(
    modifier: Modifier = Modifier,
    number: Int,
    text: String,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier.noRippleClickable { onClick() }) {
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