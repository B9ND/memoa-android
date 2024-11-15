package com.dlrjsgml.memoa.ui.component.button

import android.content.res.Resources.Theme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.theme.button_purple

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchoolButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(10.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled,
                onClick = {
                    onClick(!isSelected)
                }
            )
            .drawColoredShadow(Color.Black)
            .background(color = Color.White, shape = shape)
            .width(87.dp)
            .height(83.dp)
            .border(width = if (isSelected) 5.dp else 0.dp, color = Color(0xFFC2B9FF), shape = shape)
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .align(alignment = Alignment.Center)
                .offset(y= (-1).dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun SchoolButtonPreView() {
    SchoolButton(
        text = "1학년",
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        isSelected = true,
    ) { isSelected ->
        // 버튼의 선택 상태를 출력하거나 처리할 수 있습니다.
        println("Button is selected: $isSelected")
    }
}
