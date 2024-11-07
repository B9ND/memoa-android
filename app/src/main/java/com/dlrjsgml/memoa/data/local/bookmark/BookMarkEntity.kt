package com.dlrjsgml.memoa.data.local.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("bookmark_table")
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleId : Int
    )
