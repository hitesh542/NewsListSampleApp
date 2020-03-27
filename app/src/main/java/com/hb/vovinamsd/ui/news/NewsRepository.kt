package com.hb.vovinamsd.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hb.vovinamsd.BuildConfig
import com.hb.vovinamsd.IConstant
import com.hb.vovinamsd.api.APIInterface
import com.hb.vovinamsd.db.ArticleDao
import com.hb.vovinamsd.model.ArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiInterface: APIInterface, private val articleDao: ArticleDao) {

    private val stateObserver = MutableLiveData<IConstant.ApiState>()
    private val liveDataArticlesResponse: MediatorLiveData<ArticlesResponse> = MediatorLiveData()

    /**
     * Return LiveData<ArticlesResponse> that represent the listener of reponse data that will be
     * fetched from API or DB
     *
     * @param isFromApiOnly: IsData should return from API only
     * @return LiveData<ArticlesResponse>
     */
    fun getNewsArticles(isFromApiOnly: Boolean): LiveData<ArticlesResponse> {
        stateObserver.value = (IConstant.ApiState.LOADING)
        if (isFromApiOnly) {
            fetchArticlesFromApi()
        } else {
            val dbSource = loadFromDb()
            liveDataArticlesResponse.addSource(dbSource) { localArticleList ->
                liveDataArticlesResponse.removeSource(dbSource)
                liveDataArticlesResponse.value = ArticlesResponse(
                    status = "ok",
                    totalResults = localArticleList.size,
                    articles = localArticleList
                )
                fetchArticlesFromApi()
            }
        }
        return liveDataArticlesResponse
    }

    /**
     * Return MutableLiveData<IConstant.ApiState> that represent the listener of API state
     *
     * @return MutableLiveData<IConstant.ApiState>
     */
    fun getApiStateObserver() = stateObserver

    /**
     * This method used to fetch articles from API
     */
    private fun fetchArticlesFromApi() {
        apiInterface.getArticles().enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>?, t: Throwable?) {
                if (BuildConfig.DEBUG)
                    Log.e("Oops", "Network error ${t?.message}")
                sendError(500, t?.message)
            }

            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.code() in 200..399) {
                    val body = response.body()
                    stateObserver.value = (IConstant.ApiState.SUCCESS)
                    if (BuildConfig.DEBUG) {
                        Log.e("Article Call Status", "${body?.status}")
                        Log.e("Article Call List", "${body?.articles?.size}")
                    }
                    saveDataToDb(body)
                    liveDataArticlesResponse.value = body
                } else {
                    sendError(response.code(), response.message())
                }
            }
        })
    }

    /**
     * This method used to send the error code and error message to UI
     *
     * @param code: Error code from API
     * @param error: Error message from API
     */
    private fun sendError(code: Int, error: String?) {
        val state = IConstant.ApiState.ERROR?.apply {
            message = error
            errorCode = code
        }
        stateObserver.value = (state)
    }

    /**
     * Get all data from local DB as live data
     */
    private fun loadFromDb() = articleDao.getArticles()

    /**
     * Save data to local db
     *
     * @param articlesResponse: [ArticlesResponse]
     */
    private fun saveDataToDb(articlesResponse: ArticlesResponse?) {
        articlesResponse?.articles?.let {
            Thread {
                articleDao.deleteAllArticle()
                articleDao.addArticles(it)
            }.start()
        }
    }
}