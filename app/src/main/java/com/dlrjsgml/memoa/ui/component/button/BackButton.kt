package com.dlrjsgml.memoa.ui.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun BackButton(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.noRippleClickable(
            onClick = onClick
        )
    ) {
        Icon(
            modifier = Modifier
                .width(12.dp)
                .height(19.dp)
                .align(Alignment.CenterVertically)
                ,
            painter = painterResource(id = R.drawable.ic_back), contentDescription = null
        )

        Text(
            modifier = Modifier.padding(start = 9.dp),
            text = "뒤로가기",
            style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
        )
    }

}

@Preview
@Composable
private fun afjdafj() {
    BackButton(
        onClick = {}
    )
}