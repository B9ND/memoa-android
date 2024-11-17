package com.dlrjsgml.memoa.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.Gray5
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.caption2

@Composable
fun SettingTextField(
    modifier: Modifier = Modifier,
    hint: String,
    value: String,
    minLines: Int = 1,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit,
) {
    Column {
        BasicTextField(modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 2.dp)
            .padding(top = 8.dp, bottom = 4.dp),
            value = value,
            minLines = minLines,
            singleLine = singleLine,
            onValueChange = onValueChange,
            textStyle = boardContent.copy(fontSize = 18.sp),
            maxLines = maxLines,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = boardContent.copy(fontSize = 18.sp),
                        color = Gray40
                    )
                }
                innerTextField()
            })
        Box(modifier = modifier.height(0.7.dp).fillMaxWidth().background(Color.Black))
    }


}

@Preview
@Composable
private fun SettingTextFieldPreview() {
    SettingTextField(value = "", onValueChange = {}, hint = "변경할 이름을 입력해주세요")

}