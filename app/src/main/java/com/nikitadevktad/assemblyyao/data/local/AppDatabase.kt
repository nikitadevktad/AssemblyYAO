package com.nikitadevktad.assemblyyao.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikitadevktad.assemblyyao.data.local.dao.NewsDao
import com.nikitadevktad.assemblyyao.data.local.entity.NewsEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageItemEntity

@Database(
    entities = [NewsEntity::class, NewsPageEntity::class, NewsPageItemEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        fun create(context: Context): AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "assembly-yao.db",
        ).build()
    }
}
