package com.dlrjsgml.memoa.ui.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.component.button.MemoaImageButton
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.caption2

@Composable
fun ChangeEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    singleLine: Boolean = true,
    hintColorWhite: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
) {
    val isChange = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row {
        Spacer(modifier = Modifier.width(12.dp))
        Box(modifier = modifier.align(Alignment.CenterVertically)) {
            if (isChange.value) {
                BasicTextField(
                    modifier = modifier
                        .width(IntrinsicSize.Max)
                        .focusRequester(focusRequester)
                        .drawBehind {
                            // 밑줄을 그립니다.
                            val strokeWidth = 2.dp.toPx()
                            drawLine(
                                color = Color.Black,
                                start = androidx.compose.ui.geometry.Offset(
                                    0f,
                                    size.height - strokeWidth
                                ),
                                end = androidx.compose.ui.geometry.Offset(
                                    size.width,
                                    size.height - strokeWidth
                                ),
                                strokeWidth = strokeWidth
                            )
                        },
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = boardName,
                    keyboardActions = KeyboardActions(onDone = {isChange.value = !isChange.value}),
                    maxLines = maxLines,
                    singleLine = singleLine
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                Text(text = value, style = boardName)
            }
        }
        Spacer(modifier = Modifier.width(2.dp))
        MemoaImageButton(
            modifier = modifier.align(Alignment.CenterVertically),
            onClick = { isChange.value = !isChange.value
                      },
            content = painterResource(id = R.drawable.ic_change_name),
            noPadding = true
        )
    }
}


@Preview
@Composable
private fun adjdfkajadkfj() {
    ChangeEditText(modifier = Modifier, value = "이건희", onValueChange = {}, hint = "제목을 입력하세요")
} 