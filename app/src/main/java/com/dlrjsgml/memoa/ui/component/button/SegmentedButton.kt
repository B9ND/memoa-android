package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.theme.Gray60
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Segment(
    val selected: Boolean,
    val onClick: () -> Unit,
    val text: String,
    val enabled: Boolean = true,
)

@Composable
fun SegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    Column {
        Text(
            text = text,
            color = if (selected) Color.Black else Gray60,
            modifier = Modifier
                .clickable(
                    enabled = enabled,
                    indication = rememberBounceIndication(
                        scale = 0.95f,
                        showBackground = true,
                        radius = RoundedCornerShape(8.dp)
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                ) { onClick() }
                .padding(horizontal = 18.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun SegmentedButtonRow(
    modifier: Modifier = Modifier,
    segments: ImmutableList<Segment>,
) {
    var selectedSegment by remember { mutableStateOf(segments.first()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        segments.forEach { segment ->
            SegmentedButton(
                selected = segment == selectedSegment,
                onClick = {
                    selectedSegment = segment // 선택된 세그먼트 업데이트
                    segment.onClick() // 클릭 이벤트 처리
                },
                enabled = segment.enabled,
                text = segment.text
            )
        }
    }
}

@Preview
@Composable
fun PreviewSegmentedButtonRow() {
    SegmentedButtonRow(
        segments = persistentListOf(
            Segment(
                selected = false,
                onClick = { /* 팔로워 클릭 시 동작 */ },
                text = "팔로워"
            ),
            Segment(
                selected = false,
                onClick = { /* 팔로잉 클릭 시 동작 */ },
                text = "팔로잉"
            )
        )
    )
}
