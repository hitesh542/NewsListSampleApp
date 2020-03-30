package com.hb.vovinamsd.di

import com.hb.vovinamsd.api.APIInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideApiInterface(): APIInterface {
        return APIInterface.getNewsAPIService()
    }
}