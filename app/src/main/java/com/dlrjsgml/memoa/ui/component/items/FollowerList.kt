package com.dlrjsgml.memoa.ui.component.items

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.ui.component.button.FollowerButton
import com.dlrjsgml.memoa.ui.theme.Gray10
import com.dlrjsgml.memoa.ui.theme.Gray20
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardName

@Composable
fun FollowerList(
    name: String = "ERROR",
    date: String = "ERROR",
    title: String = "ERROR",
    profile: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyB_Y4x_2qADG2TQ2-lR8BB53v9UpL7a2Cjg&s",
) {
    Column {

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(start = 21.dp, top = 15.dp, bottom = 14.dp, end = 15.dp)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .background(color = Gray20, CircleShape)
                )
                AsyncImage(
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape),
                    model = profile,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Text(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp), text = name, style = boardName)
            Spacer(modifier = Modifier.weight(1f))
            FollowerButton(modifier = Modifier.align(Alignment.CenterVertically)) {
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
private fun afdjkdfakj(){
    FollowerList()
}