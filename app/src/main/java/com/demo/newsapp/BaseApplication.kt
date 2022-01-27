package com.demo.newsapp

import android.app.Application
import com.demo.newsapp.di.NewsInjector
import com.jakewharton.threetenabp.AndroidThreeTen

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        NewsInjector.initialize(this)
    }
}