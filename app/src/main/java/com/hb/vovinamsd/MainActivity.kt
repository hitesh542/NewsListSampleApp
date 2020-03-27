package com.hb.vovinamsd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hb.vovinamsd.ui.news.NewsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navigateToNewsFragment()
        }
    }

    /**
     * This method used to navigate to [NewsFragment]
     */
    private fun navigateToNewsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NewsFragment.newInstance())
            .commitNow()
    }
}
