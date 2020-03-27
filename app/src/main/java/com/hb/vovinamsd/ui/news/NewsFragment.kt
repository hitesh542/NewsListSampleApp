package com.hb.vovinamsd.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hb.vovinamsd.R

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

  private lateinit var viewModel: NewsViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
  }

}
