package com.hb.vovinamsd.di

import android.content.Context
import com.hb.vovinamsd.api.APIInterface
import com.hb.vovinamsd.db.ArticleDao
import com.hb.vovinamsd.db.NewsDBHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule(val context: Context) {

    @Singleton
    @Provides
    fun provideDb(): NewsDBHelper {
        return NewsDBHelper.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideArticleDao(): ArticleDao {
        return provideDb().getArticleDao()
    }
}