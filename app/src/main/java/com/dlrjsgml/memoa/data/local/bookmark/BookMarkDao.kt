package com.dlrjsgml.memoa.data.local.bookmark

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface BookMarkDao {
    @Insert
    fun insert(searchHistory: BookMarkEntity)

    @Delete
    fun delete(searchHistory: BookMarkEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark_table WHERE articleId = :articleId)")
    fun exists(articleId: Int): Boolean
    @Transaction
    fun upsert(bookMark: BookMarkEntity) : Boolean {
        if (exists(bookMark.articleId)) {
            delete(bookMark)
            return false
        } else {
            insert(bookMark)
            return true
        }
    }
}