package com.hb.vovinamsd.di

import com.hb.vovinamsd.ui.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DBModule::class, RepositoryModule::class, AdapterModule::class, ViewModelModule::class])
interface MainActivityComponent {
    fun inject(newsFragment: NewsFragment)
}