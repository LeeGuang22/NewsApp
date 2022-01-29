package com.demo.newsapp.featureNews.presentation

import androidx.lifecycle.ViewModel
import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.usecase.FetchNewsUseCase
import com.demo.newsapp.featureNews.domain.usecase.GetNewsUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface NewsViewModel {

    fun fetchNews(): Completable

    fun getNewsByTimeCreated(): Single<List<News>>

    fun getNewsByRank(): Single<List<News>>
}

class NewsViewModelImpl(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val getNewsUseCase: GetNewsUseCase
) : NewsViewModel, ViewModel() {

    override fun fetchNews(): Completable {
        return fetchNewsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNewsByTimeCreated(): Single<List<News>> {
        return getNewsUseCase.execute()
            .map { it.sortedByDescending { it.timeCreated } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNewsByRank(): Single<List<News>> {
        return getNewsUseCase.execute()
            .map {
                it.sortedWith(
                    compareBy(News::rank)
                        .thenByDescending { it.timeCreated })
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}