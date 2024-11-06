package com.dlrjsgml.memoa.data.local.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SearchHistoryDao {

    @Insert
    fun insert(searchHistory: SearchHistoryEntity)

    @Query("DELETE FROM SearchHistory WHERE id NOT IN (SELECT id FROM SearchHistory ORDER BY id DESC LIMIT 5)")
    fun deleteExcessData()

    @Transaction
    fun insertWithLimit(searchHistory: SearchHistoryEntity) {
        insert(searchHistory)
        deleteExcessData()
    }
    @Query("DELETE FROM SearchHistory")
    fun deleteAll()

    @Query("SELECT * FROM SearchHistory")
    fun getAll(): List<SearchHistoryEntity>
}
