package com.dlrjsgml.memoa.ui.component.button

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.effect.drawColoredShadow
import com.dlrjsgml.memoa.ui.component.effect.shimmerEffect
import com.dlrjsgml.memoa.ui.theme.Black20
import com.dlrjsgml.memoa.ui.theme.Gray30
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShadowButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .drawColoredShadow(Gray40)
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = onClick
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 14.dp)
            .padding(end = 12.dp)
    ) {
        Text(modifier = modifier.align(Alignment.CenterVertically), text = text, style = caption1Regular, color = if(text == "로그아웃") Color.Red else Color.Black)
        Spacer(modifier = modifier.weight(1f))
        MemoaImageButton(onClick = {onClick()}, content = painterResource(id = R.drawable.ic_back))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun fdjkaf() {
    ShadowButton(text = "소속 변경") {
        
    }
}