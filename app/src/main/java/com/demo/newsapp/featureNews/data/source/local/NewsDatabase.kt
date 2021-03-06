package com.demo.newsapp.featureNews.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.newsapp.featureNews.domain.model.News

@Database(
    entities = [News::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {

    companion object {
        internal const val DATABASE_NAME = "carousell_news.db"
    }

    abstract val newsDao: NewsDao
}