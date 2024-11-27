package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.animation.rememberBounceIndication
import com.dlrjsgml.memoa.ui.component.button.BookMarkButton
import com.dlrjsgml.memoa.ui.component.button.CommentButton
import com.dlrjsgml.memoa.ui.component.effect.shimmerEffect
import com.dlrjsgml.memoa.ui.theme.Gray10
import com.dlrjsgml.memoa.ui.theme.Gray20
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.boardContent
import com.dlrjsgml.memoa.ui.theme.boardName
import com.dlrjsgml.memoa.ui.theme.caption1Regular
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ArticleList(
    id: Int = 0,
    name: String = "로딩",
    date: String = "로딩",
    title: String = "로딩",
    image: ImmutableList<String> = persistentListOf(),
    profile: String = "",
    tag: ImmutableList<String> = persistentListOf(),
    comment: Long = 0,
    bookmarked: Boolean = false,
    onProfileClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onArticleClick: () -> Unit = {},
    onImageClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Gray10)
    )
    Column(
        modifier = Modifier
            .clickable(
                indication = rememberBounceIndication(
                    scale = 0.95f,
                    showBackground = true,
                    radius = RoundedCornerShape(8.dp)
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                onClick = {
                    onArticleClick()
//                    navController.navigate("${NavGroup.DETAIL}?$id")
                }
            )
    ) {

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(start = 21.dp, top = 15.dp, bottom = 14.dp)
        ) {
            Box(
                modifier = Modifier.clickable(
                    indication = rememberBounceIndication(
                        scale = 0.95f,
                        showBackground = true,
                        radius = RoundedCornerShape(8.dp)
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = true,
                    onClick = onProfileClick
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(color = Gray20, CircleShape)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                AsyncImage(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                       ,// 원형으로 이미지를 클립
                    model = profile,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,  // 이미지를 원에 맞춰 자르기,
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(start = 12.dp)
            ) {
                Row() {
                    Column {
                        Text(
                            modifier = Modifier,
                            text = name,
                            style = boardName.copy(fontSize = 16.sp)
                        )
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
                        text = date.substring(0..9),
                        style = boardContent.copy(fontSize = 15.sp, fontWeight = FontWeight.Medium),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = title, style = boardContent)
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyRow {
                        items(image.size) {
                            ArticleImage(image = image[it],
                                onImageClick = { onImageClick() }
                                //navController = navController
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TagLists(tag = tag)
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Row {
                        CommentButton(onClick = onCommentClick)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = comment.toString(),
                            color = Gray40,
                            style = boardContent.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row {
                        BookMarkButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            bookmarked = bookmarked,
                            onClick = {
                                onBookmarkClick()
                            }
                        )
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
}

@Composable
fun ArticleImage(image: String, onImageClick: () -> Unit) {
    var isImageLoaded by remember { mutableStateOf(false) }
    Box {
        if (!isImageLoaded) {
            // Shimmer 효과 적용
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(240.dp)
                    .shimmerEffect()
            )
        }

        AsyncImage(
            modifier = Modifier
                .width(220.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(12.dp))
                .noRippleClickable {
//                    navController.navigate("${NavGroup.IMAGEDETAIL}?$image")
                    onImageClick()
                },
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onSuccess = { isImageLoaded = true } // 이미지가 로드되면 shimmer 중단
        )
    }
}


@Composable
fun DelArticleImage(image: String, onImageClick: () -> Unit, onDelClick: () -> Unit) {
    var isImageLoaded by remember { mutableStateOf(false) }
    Box {
        if (!isImageLoaded) {
            // Shimmer 효과 적용
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(240.dp)
                    .shimmerEffect()
            )
        }

        Box() {
            AsyncImage(
                modifier = Modifier
                    .width(220.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .noRippleClickable {
//                    navController.navigate("${NavGroup.IMAGEDETAIL}?$image")
                        onImageClick()
                    },
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onSuccess = { isImageLoaded = true } // 이미지가 로드되면 shimmer 중단
            )
            Icon(
                modifier = Modifier
                    .noRippleClickable { onDelClick() }
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(34.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black
            )
        }

    }
}

@Preview
@Composable
private fun Deee() {
    DelArticleImage(
        "https://newsimg.hankookilbo.com/2015/12/17/201512171111528240_2.jpg",
        onImageClick = {},
        onDelClick = {})
}
//
//@Preview
//@Composable
//fun ArticleListPreview() {
//    ArticleList(
//        name = "김은찬",
//        date = "2024년 8월 13일",
//        title = "국어, 과학 필기 공유합니다!",
//        profile = "https://image.dongascience.com/Photo/2017/03/1489737117788.png",
//        tag = persistentListOf("국어", "과학"),
//        image = persistentListOf(
//            "https://newsimg.hankookilbo.com/cms/articlerelease/2021/04/26/813324fb-5b9a-4065-a064-cb52e7c21156.jpg",
//            "https://upload.wikimedia.org/wikipedia/commons/e/ea/Korean_Jindo_Dog.jpg"
//        )
//    )
//}