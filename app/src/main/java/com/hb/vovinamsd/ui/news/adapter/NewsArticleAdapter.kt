package com.hb.vovinamsd.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hb.vovinamsd.databinding.RowNewsArticleBinding
import com.hb.vovinamsd.di.GlideApp
import com.hb.vovinamsd.model.Article

class NewsArticleAdapter : RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>() {

    private val articles = ArrayList<Article>()

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == (newItem.title)
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    fun setData(articleList: List<Article>) {
        articles.clear()
        articles.addAll(articleList)
        mDiffer.submitList(articleList)
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder =
        NewsArticleViewHolder(RowNewsArticleBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = articles.size

    private fun getItem(position: Int): Article = articles[position]

    inner class NewsArticleViewHolder(private val binding: RowNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
            binding.ivArticleImage.loadImage(article.urlToImage!!)
        }

        private fun ImageView.loadImage(url: String?) {
            GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }
}