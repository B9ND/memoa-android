package com.dlrjsgml.memoa.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.caption2


@Composable
fun MemoaPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    secured: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
) {
    var isFocused by remember { mutableStateOf(false) }
    var isHide by remember { mutableStateOf(true) }
    val animBorderColor by animateColorAsState(
        targetValue = if (isFocused) Purple20 else Color(0xFFCCCCD6),
        label = "",
    )
    Box {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = shape
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            textStyle = caption2,
            maxLines = maxLines,
            decorationBox = { innerTextField ->

                Box(
                    modifier = modifier
                        .padding(vertical = 14.dp)
                        .padding(start = 55.dp, end = 12.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint, style = caption2,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )
        Image(
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(start = 14.dp),
            painter = painterResource(id = R.drawable.ic_token_id_text_field),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = {}
        ) {
            Icon(
                painter = painterResource(id = if(isHide)  R.drawable.ic_password_hide else R.drawable.ic_password_show),
                contentDescription = null
            )

        }

    }

}


@Composable
@Preview
fun fjadjaj(){
    MemoaPasswordTextField(
        value = "",
        onValueChange = {},
        hint = "아녕하세요"
    )
}