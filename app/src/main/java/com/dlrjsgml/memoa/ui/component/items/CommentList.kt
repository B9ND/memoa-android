package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.theme.Gray10
import com.dlrjsgml.memoa.ui.theme.Gray20
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardName

@Composable
fun CommentList(
    name: String = "ERROR",
    date: String = "ERROR",
    title: String = "ERROR",
    profile: String = "ERROR",

    ) {
    Column {

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(start = 21.dp, top = 15.dp, bottom = 14.dp)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Gray20, CircleShape)
                )
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    model = profile,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(start = 12.dp)
            ) {
                Row() {
                    Column {
                        Text(modifier = Modifier, text = name, style = boardName)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(3.5.dp)
                            .align(Alignment.CenterVertically)
                            .background(Color.Gray, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = date,
                        style = boardContent.copy(fontWeight = FontWeight.Medium),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = title, style = boardContent)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray10)
        )
    }
}

@Preview
@Composable
fun jadjf() {
    CommentList(
        name = "김은찬",
        date = "2024년 8월 13일",
        title = "국어, 과학 필기 공유합니다!",
        profile = "https://i.namu.wiki/i/PLZBtADX5SaHJjlBEq2PDLknUdpCM2mzRDdZhmnALxIDuxnypcMP0C3vq_vCa-HsQ50ECb0kFB48w8mFTz0nU6-v0ijnzMHKwzg2-JCi0dQ4XZYLIhNh-rcE_JnBEJbLHIW04BOSODr9x4rhR64S-Q.webp"
    )
}