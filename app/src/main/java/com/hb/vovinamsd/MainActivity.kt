package com.hb.vovinamsd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hb.vovinamsd.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    if (savedInstanceState == null) {
      navigateToMain()
    }
  }

  /**
   * This method used to navigate to [NewsFragment]
   */
  private fun navigateToNewsFragment(){
    supportFragmentManager.beginTransaction()
            .replace(R.id.container, NewsFragment.newInstance())
            .commitNow()
  }
}
