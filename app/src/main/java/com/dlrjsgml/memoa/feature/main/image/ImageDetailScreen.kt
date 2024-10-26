package com.dlrjsgml.memoa.feature.main.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.effect.shimmerEffect
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageDetailScreen(
    navController : NavHostController,
    imgUrl : String
) {
    val zoomState = rememberZoomState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.padding(top = 32.dp, start = 20.dp)){
            Row(
                modifier = Modifier.noRippleClickable(
                    onClick = {navController.popBackStack()}
                )
            ) {
                Image(
                    modifier = Modifier
                        .width(12.dp)
                        .height(19.dp)
                        .align(Alignment.CenterVertically)
                    ,
                    colorFilter = ColorFilter.tint(Color.White),

                    painter = painterResource(id = R.drawable.ic_back), contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(start = 9.dp),
                    color = Color.White,
                    text = "뒤로가기",
                    style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
                .zoomable(zoomState)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imgUrl,
                contentDescription = null
            )
        }
    }


}


//
//@Preview
//@Composable
//fun fadjkadjk() {
//    ImageDetailScreen()
//}