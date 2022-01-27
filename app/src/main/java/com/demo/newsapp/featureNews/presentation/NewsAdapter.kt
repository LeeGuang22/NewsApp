package com.demo.newsapp.featureNews.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.newsapp.R
import com.demo.newsapp.databinding.AdapterItemNewsBinding
import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.util.convertTime

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

    fun updateNews(news: List<News>) {
        items = news
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(val binding: AdapterItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(position: Int) {
            binding.run {
                Glide.with(itemView.context)
                    .load(items[position]
                    .bannerUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(ivBanner)
                tvTitle.text = items[position].title
                tvDescription.text = items[position].description
                tvCreatedDate.text = convertTime(items[position].timeCreated)
            }
        }
    }
}