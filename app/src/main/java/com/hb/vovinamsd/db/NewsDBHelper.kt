package com.hb.vovinamsd.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hb.vovinamsd.IConstant
import com.hb.vovinamsd.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDBHelper : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        private var db: NewsDBHelper? = null

        fun getInstance(context: Context): NewsDBHelper {
            if (db == null) {
                db = Room.databaseBuilder(context, NewsDBHelper::class.java, IConstant.DB_NAME)
                    .build()
            }
            return db!!
        }
    }

}