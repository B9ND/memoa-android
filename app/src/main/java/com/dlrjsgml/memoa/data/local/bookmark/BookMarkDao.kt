package com.dlrjsgml.memoa.data.local.bookmark

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface BookMarkDao {
    @Insert
    suspend fun insert(searchHistory: BookMarkEntity)

    @Delete
    suspend fun delete(searchHistory: BookMarkEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark_table WHERE articleId = :articleId)")
    suspend fun exists(articleId: Int): Boolean
    @Transaction
    suspend fun upsert(bookMark: BookMarkEntity) : Boolean {
        if (exists(bookMark.articleId)) {
            delete(bookMark)
            return false
        } else {
            insert(bookMark)
            return true
        }
    }
}