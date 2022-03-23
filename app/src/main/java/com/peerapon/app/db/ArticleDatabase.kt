package com.peerapon.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peerapon.data.db.dao.ArticleListDao
import com.peerapon.data.db.entity.ArticleInfoEntity

@Database(
    entities = [ArticleInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleListDao(): ArticleListDao

    companion object {
        private const val DATABASE_NAME = "APP_DB"

        @Volatile
        private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(context, ArticleDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}