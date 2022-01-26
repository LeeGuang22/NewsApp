package com.demo.newsapp.featureNews.data.source.remote

import com.demo.newsapp.featureNews.domain.model.News
import io.reactivex.Single
import retrofit2.http.GET

interface NewsService {

    companion object {
        internal const val BASE_URL = "https://storage.googleapis.com/"
    }

    @GET("carousell-interview-assets/android/carousell_news.json")
    fun fetchNews(): Single<List<News>>
}