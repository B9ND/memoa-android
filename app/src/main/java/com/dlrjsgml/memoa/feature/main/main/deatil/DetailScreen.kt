package com.dlrjsgml.memoa.feature.main.main.deatil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.R
import com.dlrjsgml.memoa.feature.main.write.WriteViewModel
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.button.BookMarkButton
import com.dlrjsgml.memoa.ui.component.button.CommentButton
import com.dlrjsgml.memoa.ui.component.items.CommentList
import com.dlrjsgml.memoa.ui.component.items.TagLists
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardContent1
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = viewModel(),
    navController: NavHostController,
    boardNumber: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            BackButton {
                navController.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CommentList(
            name = "TODO",
            date = "2024년 8월 13일",
            title = "TODO",
            profile = "https://i.namu.wiki/i/PLZBtADX5SaHJjlBEq2PDLknUdpCM2mzRDdZhmnALxIDuxnypcMP0C3vq_vCa-HsQ50ECb0kFB48w8mFTz0nU6-v0ijnzMHKwzg2-JCi0dQ4XZYLIhNh-rcE_JnBEJbLHIW04BOSODr9x4rhR64S-Q.webp"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Column {
                Text(
                    text = "글 번호:$boardNumber 글글" +
                            "글" +
                            "글" +
                            "글" +
                            "글" +
                            "\n글글글" +
                            "\nrmjkadfkjdfakjadfadfsdfafa",
                    style = boardContent
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            TagLists(persistentListOf("국어","과학"), mini = true)
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Row {
                    CommentButton(onClick = {navController.navigate("${NavGroup.COMMENT}/phone=ddddddd")})
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "todo",
                        color = Gray40,
                        style = boardContent.copy(fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    BookMarkButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {})
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "",
                        color = Gray40,
                        style = boardContent.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }


    }
}

//@Preview
//@Composable
//fun DetailPreview() {
//    DetailScreen(boardNumber = 1)
//}