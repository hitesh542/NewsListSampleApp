package com.hb.vovinamsd.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ArticlesResponse(
    @SerializedName("status") var status: String?,
    @SerializedName("totalResults") var totalResults: Int?,
    @SerializedName("articles") var articles: List<Article>?
)

@Entity(tableName = "article")
data class Article(
    @SerializedName("author") var author: String?,
    @PrimaryKey
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String?,
    @SerializedName("url") var url: String?,
    @SerializedName("urlToImage") var urlToImage: String?,
    @SerializedName("publishedAt") var publishedAt: String?,
    @SerializedName("content") var content: String?
)