package com.dlrjsgml.memoa.ui.component.textfield

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.theme.caption2


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemoaDropDownTextField(
    modifier: Modifier = Modifier,
    hint: AnnotatedString,
    shape: Shape = RoundedCornerShape(12.dp),
    textButtonOnClick: () -> Unit = {},
    value: String = "",
    expandStatus: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    val (nothing) = remember { mutableStateOf("") }
    var dp by remember { mutableStateOf("") }
    dp = ""
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(
                color = Color.White,
                shape = shape
            )
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .clickable {
                textButtonOnClick
            }
    ) {
        Row(
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(vertical = 8.dp)
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_token_id_text_field),
                contentDescription = null
            )
            Spacer(Modifier.width(2.dp))
            BasicTextField(
                value = nothing,
                onValueChange = {},
                textStyle = caption2,
                readOnly = true,
                maxLines = 1,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = modifier
                            .width(195.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                text = hint,
                                style = caption2.copy(fontSize = 16.sp),
                                maxLines = 1
                            )
                        }
                        innerTextField()
                    }
                },
            )
            Image(
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(10.dp)
                    .rotate(if (expandStatus) 0F else 180F),
                painter = painterResource(
                    id = R.drawable.ic_dropdown
                )
            )
            Spacer(modifier.width(10.dp))
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MemoaDropDownTextFieldPreview() {
    MemoaDropDownTextField(
        hint = buildAnnotatedString {
            append("가장좋은학교는우리소프트웨어마이스터고등학교")
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}
