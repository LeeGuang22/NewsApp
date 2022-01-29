package com.demo.newsapp.featureNews.presentation.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class SchedulerRule : TestRule {

    private val scheduler = Schedulers.trampoline()

    override fun apply(base: Statement, desc: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
