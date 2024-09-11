package com.dlrjsgml.memoa.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.miniCaption2


@Composable
fun DropDown(
    text: String,
    onClick: () -> Unit,
    selectList: List<String> = emptyList(),
    onTextChanged: (String) -> Unit // 콜백 추가
) {
    var selectedText by remember { mutableStateOf(text) }
    val isSelected = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f, showBackground = true, radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {
                    onClick()
                    isSelected.value = !isSelected.value
                })
            .background(color = Purple20, shape = RoundedCornerShape(6.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(start = 23.dp, end = 29.dp)
        ) {
            Text(text = selectedText, style = miniCaption2)
        }
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .rotate(if (isSelected.value) 180F else 0F), painter = painterResource(
                id = R.drawable.ic_dropdown
            ), contentDescription = null
        )
    }

    if (isSelected.value) {
        LazyColumn(modifier = Modifier.padding(top = 24.dp)) {
            items(selectList.size) { index ->
                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            selectedText = selectList[index]
                            isSelected.value = false
                            onTextChanged(selectedText) // 텍스트 변경 시 콜백 호출
                        }
                    )
                ) {
                    Text(text = selectList[index])
                }
            }
        }
    }
}

@Preview
@Composable
fun DropDownPreView() {
    DropDown(
        text = "대구소프트웨어마이스터고등학교",
        selectList = listOf("1학년", "2학년", "3학년"),
        onClick = { /*TODO*/ },
        onTextChanged = { newText ->
            println("Selected text: $newText")
        }
    )
}