package com.hb.vovinamsd.api

import com.hb.vovinamsd.BuildConfig
import com.hb.vovinamsd.IConstant
import com.hb.vovinamsd.StaticData
import com.hb.vovinamsd.model.ArticlesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    companion object {

        private val okHttpClient by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        }

        private val apiInterface: APIInterface by lazy {
            Retrofit.Builder()
                .baseUrl(StaticData().stringFromJNI("BASE_URL"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface::class.java)
        }

        fun getNewsAPIService() = apiInterface
    }

    @GET("everything")
    fun getArticles(
        @Query("sources") source: String = IConstant.Source.TECH_CRUNCH.key,
        @Query("sortBy") sortBy: String? = IConstant.SortBy.PUBLISHED_AT.key,
        @Query("apiKey") apiKey: String = IConstant.API_KEY
    ): Call<ArticlesResponse>
}