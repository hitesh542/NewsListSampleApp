package com.hb.vovinamsd.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hb.vovinamsd.api.APIInterface
import com.hb.vovinamsd.model.ArticlesResponse

class NewsViewModel : ViewModel() {
    private val newsRepo: NewsRepository = NewsRepository(APIInterface.getNewsAPIService())

    fun getNewsArticles(): LiveData<ArticlesResponse> {
        return newsRepo.getNewsArticles()
    }

    fun getApiStateObserver() = newsRepo.getApiStateObserver()
}
