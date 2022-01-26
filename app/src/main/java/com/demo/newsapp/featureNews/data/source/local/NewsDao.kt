package com.demo.newsapp.featureNews.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.newsapp.featureNews.domain.model.News
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<News>)

    @Query("SELECT * FROM news")
    fun getNews(): Single<List<News>>
}