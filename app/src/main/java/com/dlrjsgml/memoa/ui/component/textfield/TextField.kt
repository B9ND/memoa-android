package com.dlrjsgml.memoa.ui.component.textfield

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption2


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemoaTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    secured: Boolean = false,
    shape: Shape = RoundedCornerShape(12.dp),
    textButton: Boolean = false,
    textButtonVal: String = "",
    textButtonOnClick: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
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
                }
//                .border(
//                    width = 4.dp,
//                    color = animBorderColor,
//                    shape = shape
//                )
            ,
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
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = hint, style = caption2.copy(fontSize = 16.sp),
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )
        Row(
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .padding(start = 6.dp)
                    ,
                painter = painterResource(id = R.drawable.ic_token_id_text_field),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))

            if (textButton) {
                TextButton(onClick = textButtonOnClick) {
                    Text(text = textButtonVal, style = caption2, color = Purple60)
                }
            }

        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MemoaTextFieldPreview() {
    MemoaTextField(
        value = "",
        onValueChange = {},
        hint = "아녕하세요",
        textButton = true,
        textButtonVal = "인증"
    )
}