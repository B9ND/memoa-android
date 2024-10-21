package com.dlrjsgml.memoa.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dlrjsgml.memoa.MemoaApplication
import com.dlrjsgml.memoa.data.local.search.SearchHistoryDao
import com.dlrjsgml.memoa.data.local.search.SearchHistoryEntity


@Database(
    entities = [SearchHistoryEntity::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(
                        MemoaApplication.getContext(),
                        UserDatabase::class.java,
                        "user-database"
                    ).build()
                }
            }
            return instance
        }
    }
}