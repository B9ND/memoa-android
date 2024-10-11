package com.dlrjsgml.memoa.ui.component.items

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dlrjsgml.memoa.ui.theme.Gray40
import com.dlrjsgml.memoa.ui.theme.boardContent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TagLists(tag: ImmutableList<String> = persistentListOf(),mini : Boolean = false) {
    LazyRow {
        items(tag.size) {
            Text(
                text = "#${tag[it]}",
                color = Gray40,
                style = boardContent.copy(fontSize = if (mini) 12.sp else 16.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}