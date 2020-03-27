package com.hb.vovinamsd.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hb.vovinamsd.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(articleList: List<Article>)

    @Query("DELETE FROM article")
    fun deleteAllArticle()

    @Query("SELECT * FROM article")
    fun getArticles(): LiveData<List<Article>>
}