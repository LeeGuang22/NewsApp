package com.demo.newsapp.featureNews.data.repository

import com.demo.newsapp.featureNews.data.source.local.NewsDao
import com.demo.newsapp.featureNews.data.source.remote.NewsService
import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.repository.NewsRepository
import io.reactivex.Completable
import io.reactivex.Single

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsService,
    private val newsLocalDataSource: NewsDao
): NewsRepository {
    override fun fetchNews(): Completable {
        return newsRemoteDataSource.fetchNews()
            .flatMapCompletable { insertNews(it) }
    }

    override fun getNewsFromDb(): Single<List<News>> {
        return newsLocalDataSource.getNews()
    }

    fun insertNews(news: List<News>): Completable {
        return Completable.fromCallable {
            newsLocalDataSource.insertNews(news)
        }
    }
}