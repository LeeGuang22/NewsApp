package com.demo.newsapp.di

import android.app.Application

object NewsInjector {
    lateinit var newsComponent: NewsComponent
        private set

    fun initialize(application: Application) {
        if (!NewsInjector::newsComponent.isInitialized) {
            newsComponent = DaggerNewsComponent.builder()
                .newsModule(NewsModule(application))
                .build()
        }
    }
}