package com.demo.newsapp.featureNews.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "banner_url")
    val bannerUrl: String,

    @ColumnInfo(name = "time_created")
    val timeCreated: Long,

    @ColumnInfo(name = "rank")
    val rank: Int
)
