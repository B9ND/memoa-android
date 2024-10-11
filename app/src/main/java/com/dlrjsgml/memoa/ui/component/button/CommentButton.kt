package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable

@Composable
fun CommentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        modifier = modifier.noRippleClickable { onClick()},
        painter = painterResource(id = R.drawable.ic_comment),
        contentDescription = null
    )
}