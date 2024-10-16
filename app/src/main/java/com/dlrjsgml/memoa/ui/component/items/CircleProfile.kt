package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.feature.TestScreen
import com.dlrjsgml.memoa.ui.component.effect.shimmerEffect
import com.dlrjsgml.memoa.ui.theme.Gray5
import com.dlrjsgml.memoa.ui.theme.Purple60

@Composable
fun CircleProfile(
    modifier: Modifier = Modifier,
    profile: String,
) {
    Box(
        modifier = modifier
            .border(shape = CircleShape, color = Color.White, width = 7.dp)
            .size(120.dp)
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .shimmerEffect()

        )
        AsyncImage(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
            model = profile,
            contentDescription = "오류",
            contentScale = ContentScale.Crop
        )

    }

}

@Preview
@Composable
private fun afdjafdjdaf() {
    CircleProfile(profile = "https://newsimg.hankookilbo.com/cms/articlerelease/2021/04/26/813324fb-5b9a-4065-a064-cb52e7c21156.jpg")
}