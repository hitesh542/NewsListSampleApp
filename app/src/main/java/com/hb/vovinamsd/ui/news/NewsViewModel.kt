package com.hb.vovinamsd.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hb.vovinamsd.model.ArticlesResponse
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepo : NewsRepository) : ViewModel() {

    fun getNewsArticles(isFromApiOnly: Boolean): LiveData<ArticlesResponse> {
        return newsRepo.getNewsArticles(isFromApiOnly)
    }

    fun getApiStateObserver() = newsRepo.getApiStateObserver()
}
