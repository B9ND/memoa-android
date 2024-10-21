package com.dlrjsgml.memoa.data.local.search

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


}