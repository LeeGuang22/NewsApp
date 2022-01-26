package com.demo.newsapp.featureNews.domain.usecase

import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.repository.NewsRepository
import io.reactivex.Single

interface GetNewsUseCase {
    fun invoke(): Single<List<News>>
}

class GetNewsUseCaseImpl(
    private val repository: NewsRepository
): GetNewsUseCase {

    override fun invoke(): Single<List<News>> {
        return repository.getNewsFromDb()
    }
}