package com.hb.vovinamsd.di

import com.hb.vovinamsd.api.APIInterface
import com.hb.vovinamsd.db.ArticleDao
import com.hb.vovinamsd.ui.news.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun getNewsRepository(apiInterface: APIInterface, articleDao: ArticleDao) : NewsRepository{
       return NewsRepository(apiInterface, articleDao)
    }
}