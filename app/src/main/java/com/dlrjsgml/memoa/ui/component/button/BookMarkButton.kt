package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable

@Composable
fun BookMarkButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {

    val bookmarkIsSelected = remember {
        mutableStateOf(false)
    }
    val bookmarkImage =
        if (bookmarkIsSelected.value) painterResource(id = R.drawable.ic_selectbook) else painterResource(
            id = R.drawable.ic_smallbookmark
        )
    Box(modifier = modifier.noRippleClickable(onClick = onClick)) {
        Image(
            modifier = modifier
                .noRippleClickable(
                    onClick = {
                        bookmarkIsSelected.value = !bookmarkIsSelected.value
                    },
                ), painter = bookmarkImage, contentDescription = null
        )
    }

}