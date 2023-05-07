package com.mangbaam.mvitemplate.sample.newssearch.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mangbaam.mvitemplate.R
import com.mangbaam.mvitemplate.databinding.ItemArticleBinding
import com.mangbaam.mvitemplate.sample.newssearch.model.Article

class SearchListAdapter : ListAdapter<Article, SearchListAdapter.ArticleViewHolder>(diffUtil) {
    class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        DataBindingUtil.inflate<ItemArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false,
        ).root,
    ) {
        private val binding = DataBindingUtil.bind<ItemArticleBinding>(itemView) ?: error("")

        fun bind(article: Article) {
            binding.article = article
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
