package com.hb.vovinamsd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.espresso.base.MainThread
import androidx.test.platform.app.InstrumentationRegistry
import com.hb.vovinamsd.db.NewsDBHelper
import com.hb.vovinamsd.model.Article
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * This class used to test DB functions and operations
 */
class ArticleDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsDBHelper: NewsDBHelper

    @Before
    fun initDatabase() {
        newsDBHelper = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            NewsDBHelper::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDatabase() {
        newsDBHelper.close()
    }

    @Test
    @MainThread
    fun testInsertAndRetrieve() {
        val dummyList1 = getArticleDummyList()
        val dummyList2 = getArticleList()

        newsDBHelper.getArticleDao().addArticles(dummyList1)
        runBlocking {
            val dbList = newsDBHelper.getArticleDao().getArticles().getOrAwaitValue()
            Assert.assertTrue(
                dummyList1.containsAll(
                    dbList
                )
            )

            newsDBHelper.getArticleDao().addArticles(dummyList2)
            val list = ArrayList<Article>()
            list.addAll(dummyList1)
            list.addAll(dummyList2)
            val dbList2 = newsDBHelper.getArticleDao().getArticles().getOrAwaitValue()
            assertTrue(
                list.containsAll(
                    dbList2
                )
            )
        }
    }

    @Test
    fun testDelete() {
        newsDBHelper.getArticleDao().addArticles(getArticleDummyList())
        newsDBHelper.getArticleDao().deleteAllArticle()
        assertTrue(newsDBHelper.getArticleDao().getArticles().getOrAwaitValue().isEmpty())
    }

    /**
     * Copyright 2019 Google LLC.
     * SPDX-License-Identifier: Apache-2.0
     */
    private fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        this.observeForever { o ->
            data[0] = o
            latch.countDown()
        }
        latch.await(time, timeUnit)

        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }

    private fun getArticleList(): List<Article> {
        val article = Article(
            "Article1", "COVID-19", "Hitesh",
            "This is the dummy article description", "no url string",
            "no url for thumbnail", "noDate"
        )

        val articleDummyList = ArrayList<Article>()
        articleDummyList.add(article)
        return articleDummyList
    }

    private fun getArticleDummyList(): List<Article> {
        val article2 = Article(
            "Article2", "Indian-News", "Hiren",
            "This is the dummy article description", "no url string",
            "no url for thumbnail", "noDate"
        )

        val articleDummyList = ArrayList<Article>()
        articleDummyList.addAll(getArticleList())
        articleDummyList.add(article2)
        return articleDummyList
    }
}