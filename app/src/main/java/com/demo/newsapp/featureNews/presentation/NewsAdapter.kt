package com.demo.newsapp.featureNews.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.newsapp.R
import com.demo.newsapp.databinding.AdapterItemNewsBinding
import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.util.toStringFormat

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =AdapterItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.bindView(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal fun updateNews(news: List<News>) {
        items = news
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(val binding: AdapterItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bindView(position: Int) {
            binding.run {
                val item = items[position]
                bindImageView(item, ivBanner)
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvCreatedDate.text = item.timeCreated.toStringFormat()
            }
        }

        private fun bindImageView(item: News, ivBanner: ImageView) {
            Glide.with(itemView.context)
                .load(item.bannerUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(ivBanner)
        }
    }
}