package com.demo.newsapp

import android.app.Application
import com.demo.newsapp.di.NewsInjector

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        NewsInjector.initialize(this)
    }
}