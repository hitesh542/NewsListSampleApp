package com.hb.vovinamsd.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hb.vovinamsd.IConstant
import com.hb.vovinamsd.R
import com.hb.vovinamsd.api.APIInterface
import com.hb.vovinamsd.db.ArticleDao
import com.hb.vovinamsd.di.DBModule
import com.hb.vovinamsd.di.DaggerMainActivityComponent
import com.hb.vovinamsd.model.ArticlesResponse
import com.hb.vovinamsd.ui.news.adapter.NewsArticleAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

/**
 * This fragment is used to show the news listing
 *
 * Actionable tasks:
 *
 * 1. Fetch the news from API and save to local DB if internet connectivity
 * 2. Show news list from local DB
 *
 */
class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    @Inject
    lateinit var adapter: NewsArticleAdapter
    @Inject
    lateinit var apiInterface: APIInterface
    @Inject
    lateinit var articleDao: ArticleDao
    @Inject
    lateinit var newsRepository: NewsRepository
    @Inject
    lateinit var viewModel: NewsViewModel

    private val apiStateObserver by lazy {
        Observer<IConstant.ApiState> {
            if (it != null) {
                when (it) {
                    IConstant.ApiState.LOADING -> {
                        showLoading()
                    }
                    IConstant.ApiState.ERROR -> {
                        hideLoading()
                        showApiError(it)
                    }
                    IConstant.ApiState.SUCCESS -> {
                        hideLoading()
                    }
                }
            }
        }
    }

    private val articleObserver by lazy {
        Observer<ArticlesResponse> {
            if (it != null) {
                setData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Inject all the object here at once using Dagger 2
        val component = DaggerMainActivityComponent
            .builder()
            .dBModule(DBModule(activity!!))
            .build()
        component.inject(this)

        viewModel.getApiStateObserver().observe(viewLifecycleOwner, apiStateObserver)

        recyclerView.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            fetchData(true)
        }
        fetchData(false)
    }

    /**
     * This method used to fetch the data from [NewsRepository]
     */
    private fun fetchData(isFromApiOnly: Boolean) {
        viewModel.getNewsArticles(isFromApiOnly).observe(viewLifecycleOwner, articleObserver)
    }

    /**
     * This method used to show loading dialog
     */
    private fun showLoading() {
        if (!swipeRefresh.isRefreshing)
            progressBar.show()
    }

    /**
     * This method used to hide the loading dialog
     */
    private fun hideLoading() {
        if (swipeRefresh.isRefreshing)
            swipeRefresh.isRefreshing = false
        else
            progressBar.hide()
    }

    /**
     * This method used to show error message
     *
     * @param apiState: [com.hb.vovinamsd.IConstant.ApiState.ERROR]
     */
    private fun showApiError(apiState: IConstant.ApiState) {
        activity?.let {
            Toast.makeText(
                it,
                apiState.message ?: "Unknown error! Please try again",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setData(articlesResponse: ArticlesResponse) {
        articlesResponse.articles?.let {
            adapter.setData(it)
        }
    }
}
