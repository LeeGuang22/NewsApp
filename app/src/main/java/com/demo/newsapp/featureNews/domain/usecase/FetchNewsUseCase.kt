package com.demo.newsapp.featureNews.domain.usecase

import com.demo.newsapp.featureNews.domain.repository.NewsRepository
import io.reactivex.Completable

interface FetchNewsUseCase {
    fun invoke(): Completable
}

class FetchNewsUseCaseImpl(
    val repository: NewsRepository
): FetchNewsUseCase {
    override fun invoke(): Completable {
        return repository.fetchNews()
    }
}