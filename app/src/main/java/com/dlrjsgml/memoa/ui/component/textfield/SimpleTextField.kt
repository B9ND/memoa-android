package com.dlrjsgml.memoa.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Gray5
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption2
import com.dlrjsgml.memoa.ui.theme.search1

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    singleLine: Boolean = true,
    hintColorWhite : Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    shape: Shape = RoundedCornerShape(10.dp),
) {
    var isFocused by remember { mutableStateOf(false) }
    val animBorderColor by animateColorAsState(
        targetValue = if (isFocused) Purple60 else Gray30,
        label = "",
    )
    BasicTextField(modifier = modifier
        .fillMaxWidth()
        .border(width = 2.dp, color = animBorderColor, shape = shape)
        .background(Color.White, shape = shape)
        .onFocusChanged {
            isFocused = it.isFocused
        }
        .padding(start = 12.dp)
        .padding(vertical = 9.dp),
        value = value,
        minLines = minLines,
        singleLine = singleLine,
        onValueChange = onValueChange,
        textStyle = search1,
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = hint,
                    style = search1,
                    color = if(hintColorWhite)Gray5 else Gray60
                )
            }
            innerTextField()
        })
}

@Preview
@Composable
private fun Sjkdkja() {
    SimpleTextField( singleLine = false, minLines = 6,maxLines = 4, value = "d", onValueChange = {}, hint = "제목을 입력하세요")
}
