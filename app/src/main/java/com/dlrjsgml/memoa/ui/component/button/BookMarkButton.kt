package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable

@Composable
fun BookMarkButton(
    modifier: Modifier = Modifier,
    bookmarked : Boolean = false,
    onClick: () -> Unit = {},

) {
    var tempState by remember { mutableStateOf(bookmarked) }
    val bookmarkImage =
        if (tempState) painterResource(id = R.drawable.ic_selectbook) else painterResource(
            id = R.drawable.ic_smallbookmark
        )
    Box(modifier = modifier.noRippleClickable(onClick = onClick)) {
        Image(
            modifier = modifier
                .noRippleClickable(
                    onClick = {
                        onClick()
                        tempState = !tempState
                    },
                ), painter = bookmarkImage, contentDescription = null
        )
    }
}