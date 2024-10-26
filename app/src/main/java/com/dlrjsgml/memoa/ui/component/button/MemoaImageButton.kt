package com.dlrjsgml.memoa.ui.component.button


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MemoaImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: Painter,
    noPadding: Boolean = false
) {
    Image(
        modifier = modifier
            .noRippleClickable(
                onClick = {
                    onClick()
                }
            )
            .padding(if(noPadding)0.dp else 9.dp),
        painter = content, contentDescription = null)
}