package com.dlrjsgml.memoa.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Purple20
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun MemoaDropDown(

    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    selectList: List<String> = emptyList(),
    onTextChanged: (String) -> Unit,
) {
    var expandStatus by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(
        selectList[0])}
    val isSelected = remember { mutableStateOf(true) }


    Box(modifier = modifier
        .padding(4.dp)
        .clickable(indication = rememberBounceIndication(
            scale = 0.95f, showBackground = true, radius = RoundedCornerShape(8.dp)
        ), interactionSource = remember { MutableInteractionSource() }, onClick = {
            expandStatus = true
        })
        .background(color = Purple20, shape = shape)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 7.dp)
                .padding(start = 14.dp, end = 10.dp)

        ) {

            Text(
                text = selectedText,
                color = Black,
                style = caption1Regular.copy(fontSize = 14.sp),
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(2.dp))
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .rotate(if (expandStatus) 0F else 180F), painter = painterResource(
                    id = R.drawable.ic_dropdown

                ), contentDescription = null
            )

        }





        DropdownMenu(
            modifier = modifier.background(color = White),
            expanded = expandStatus,
            onDismissRequest = {
                expandStatus = false
            },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            selectList.forEachIndexed { index, item ->
                DropdownMenuItem(

                    text = {
                        Text(
                            text = item,
                            style = boardContent1.copy(fontWeight = FontWeight.Medium),
                            color = if (selectedText == item) Gray else Black,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        selectedText = selectList[index]
                        isSelected.value = false
                        expandStatus = false
                        onTextChanged(selectedText)
                    })
            }
        }
    }
}


@Preview
@Composable
private fun DropDownTwoPreView() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 8.dp, end = 16.dp, top = 10.dp)
            .padding(vertical = 8.dp)
    ) {
        MemoaDropDown(
            selectList = listOf("대구소프트웨어마이스터고등학교", "교학웨트프소구대"),
        ) {
        }
        MemoaDropDown(
            selectList = listOf("과목", "2학년", "3학년"),
        ) {
        }
        MemoaDropDown(
            selectList = listOf("1학년", "2학년", "3학년"),
        ) {
        }
    }
}