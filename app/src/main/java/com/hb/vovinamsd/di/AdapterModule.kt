package com.hb.vovinamsd.di

import com.hb.vovinamsd.ui.news.adapter.NewsArticleAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {
    @Provides
    fun getNewsArticleAdapter() = NewsArticleAdapter()
}