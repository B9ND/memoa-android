package com.dlrjsgml.memoa.ui.component.textfield


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.component.button.MemoaImageButton
import com.dlrjsgml.memoa.ui.theme.Gray25
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.Gray5
import com.dlrjsgml.memoa.ui.theme.Gray60
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.searchMini

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    singleLine: Boolean = true,
    hintColorWhite: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    shape: Shape = RoundedCornerShape(30.dp),
    onClick: () -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isFocused by remember { mutableStateOf(false) }
    val animBorderColor by animateColorAsState(
        targetValue = if (isFocused) Purple60 else Gray30,
        label = "",
    )
    Box {
        BasicTextField(modifier = modifier
            .fillMaxWidth()
            .background(Gray25, shape = shape)
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .align(Alignment.Center)
            .padding(start = 46.dp, end = 12.dp)
            .padding(vertical = 10.dp),
            value = value,
            minLines = minLines,
            singleLine = singleLine,
            onValueChange = onValueChange,
            textStyle = searchMini.copy(color = Gray40),
            maxLines = maxLines,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = hint,
                        style = searchMini.copy(color = Gray40),
                        color = if (hintColorWhite) Gray5 else Gray60
                    )
                }
                innerTextField()
            })

        MemoaImageButton(
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(start = 6.dp,top = 0.dp),
            onClick = { onClick()
                      },
            content = painterResource(id = R.drawable.ic_searchcolor)
        )

    }

}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(
        singleLine = false,
        minLines = 1,
        maxLines = 1,
        value = "검색어를 입력하세dd요",
        onValueChange = {},
        hint = "검색어를 입력하세요"
    )


}