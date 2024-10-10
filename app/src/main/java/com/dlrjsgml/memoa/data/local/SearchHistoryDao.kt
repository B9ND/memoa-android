package com.dlrjsgml.memoa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchHistoryDao {





    @Insert
    fun insert(searchHistory: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistory")
    fun getAll(): List<SearchHistoryEntity>


    @Query("DELETE FROM SEARCHHISTORY")
    fun deleteAll()

    @Query("DELETE FROM SEARCHHISTORY WHERE id IN (SELECT id FROM SearchHistory ORDER BY id ASC LIMIT 5)")
    suspend fun deleteFirstFive()



}