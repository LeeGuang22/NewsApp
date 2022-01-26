package com.demo.newsapp.di

import com.demo.newsapp.featureNews.presentation.NewsActivity
import dagger.Component
import javax.inject.Scope

@Component(modules = [NewsModule::class])
interface NewsComponent {
    fun inject(newsActivity: NewsActivity)
}

@Scope
@Retention
annotation class NewsScope