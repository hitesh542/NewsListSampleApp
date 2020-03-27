package com.hb.vovinamsd.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hb.vovinamsd.db.ArticleDao

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val articleDao: ArticleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(articleDao = articleDao) as T
    }
}