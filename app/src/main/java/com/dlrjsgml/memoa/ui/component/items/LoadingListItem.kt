package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dlrjsgml.memoa.R
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JJapList(
) {


    Column(
        modifier = Modifier

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray10)
        )
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
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .padding(start = 12.dp)
            ) {
                Row() {
                    Column {
                        Box(
                            modifier = Modifier
                                .width(55.dp)
                                .height(18.dp)
                                .shimmerEffect()
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
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(18.dp)
                            .shimmerEffect()
                    )

                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(260.dp)
                        .height(18.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(18.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyRow {
                        items(4) {
                            JJapArticleImage()
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(20.dp)
                            .shimmerEffect()
                    )
                    Spacer(Modifier.width(2.dp))
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(20.dp)
                            .shimmerEffect()
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Row {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .width(17.dp)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .width(17.dp)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JJapArticleImage() {

    Box {
        Box(
            modifier = Modifier
                .width(220.dp)
                .height(240.dp)
                .shimmerEffect()
        )


    }
}

@Preview
@Composable
fun afdjkadfjk() {
    JJapList()
}
