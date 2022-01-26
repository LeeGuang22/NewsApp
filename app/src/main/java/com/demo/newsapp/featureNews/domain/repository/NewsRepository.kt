package com.demo.newsapp.featureNews.domain.repository

import com.demo.newsapp.featureNews.domain.model.News
import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {

    fun fetchNews(): Completable

    fun getNewsFromDb(): Single<List<News>>
}