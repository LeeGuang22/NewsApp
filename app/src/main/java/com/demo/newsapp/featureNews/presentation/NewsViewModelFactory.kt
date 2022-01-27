package com.demo.newsapp.featureNews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.newsapp.featureNews.domain.usecase.FetchNewsUseCase
import com.demo.newsapp.featureNews.domain.usecase.GetNewsUseCase

class NewsViewModelFactory(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val getNewsUseCase: GetNewsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModelImpl(
            fetchNewsUseCase,
            getNewsUseCase
        ) as T
    }
}