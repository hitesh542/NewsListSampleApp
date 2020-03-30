package com.hb.vovinamsd.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hb.vovinamsd.ui.custom.ViewModelFactory
import com.hb.vovinamsd.ui.news.NewsRepository
import com.hb.vovinamsd.ui.news.NewsViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun getNewsViewModel(newsRepository: NewsRepository) = NewsViewModel(newsRepository)

    @Provides
    fun getViewModelFactory(map: Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>): ViewModelProvider.Factory {
        return ViewModelFactory(map)
    }
}