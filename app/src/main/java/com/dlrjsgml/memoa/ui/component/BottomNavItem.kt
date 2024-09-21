package com.dlrjsgml.memoa.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.miniCaption1

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    isSelected: Boolean,
    text: String,
) {
    val sizes = if (resId == R.drawable.ic_search) 32.dp else 24.dp
    Column(modifier = modifier.padding(12.dp)) {
        Image(
            modifier = Modifier
                .size(
                    30.dp
                )
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = resId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(if (isSelected) Purple60 else Color.Black)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = text,
            style = if (isSelected) miniCaption1.copy(color = Purple60) else miniCaption1.copy(color = Color.Black)
        )
    }
}

@Composable
fun BottomCircle(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
) {
    Column {

        Box(
            modifier = modifier
                .background(shape = CircleShape, color = Color.White)
                .size(90.dp)
        ) {
            Image(
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 15.dp),
                colorFilter = ColorFilter.tint(if (isSelected) Purple60 else Color.Black),
                painter = painterResource(id = R.drawable.ic_plus), contentDescription = null
            )
            Text(
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 21.89.dp),
                text = "메모 작성",
                style = if (isSelected) miniCaption1.copy(color = Purple60) else miniCaption1.copy(
                    color = Color.Black
                ),
            )
        }
    }
}


@Composable
fun BottomCircleTwo(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
) {
    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(topStart = 1000.dp, topEnd = 1000.dp),
                color = Color.White
            )
            .width(90.dp)
            .height(70.dp)
    ) {
        Image(
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(top = 17.dp),
            colorFilter = ColorFilter.tint(if (isSelected) Purple60 else Color.Black),
            painter = painterResource(id = R.drawable.ic_plus), contentDescription = null
        )
        Text(
            modifier = modifier
                .padding(top = 50.dp)
                .align(Alignment.BottomCenter), text = "메모 작성",
            style = if (isSelected) miniCaption1.copy(color = Purple60) else miniCaption1.copy(
                color = Color.Black
            ),
        )
    }

}

@Preview
@Composable
private fun fajddafj() {
    Column {
        BottomCircleTwo(isSelected = false)
        BottomCircle(isSelected = false)
    }


}
//@Preview
//@Composable
//fun adjadfj() {
//    BottomNavItem(resId = com.dlrjsgml.memoa.R.drawable.ic_home, isSelected = false, text = "t메인")
//}