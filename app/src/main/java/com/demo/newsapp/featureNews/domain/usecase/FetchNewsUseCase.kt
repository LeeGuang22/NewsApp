package com.demo.newsapp.featureNews.domain.usecase

import com.demo.newsapp.featureNews.domain.repository.NewsRepository
import io.reactivex.Completable

interface FetchNewsUseCase {
    fun execute(): Completable
}

class FetchNewsUseCaseImpl(
    val repository: NewsRepository
): FetchNewsUseCase {
    override fun execute(): Completable {
        return repository.fetchNews()
    }
}