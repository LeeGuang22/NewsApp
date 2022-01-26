package com.demo.newsapp.di

import android.app.Application
import androidx.room.Room
import com.demo.newsapp.featureNews.data.repository.NewsRepositoryImpl
import com.demo.newsapp.featureNews.data.source.local.NewsDao
import com.demo.newsapp.featureNews.data.source.local.NewsDatabase
import com.demo.newsapp.featureNews.data.source.remote.NewsService
import com.demo.newsapp.featureNews.domain.repository.NewsRepository
import com.demo.newsapp.featureNews.domain.usecase.FetchNewsUseCase
import com.demo.newsapp.featureNews.domain.usecase.FetchNewsUseCaseImpl
import com.demo.newsapp.featureNews.domain.usecase.GetNewsUseCase
import com.demo.newsapp.featureNews.domain.usecase.GetNewsUseCaseImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NewsModule(private val application: Application) {

    @Provides
    @NewsScope
    fun provideNewsDatabase(): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            NewsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @NewsScope
    fun provideFetchNewsUseCase(
        newsRepository: NewsRepository
    ): FetchNewsUseCase {
        return FetchNewsUseCaseImpl(newsRepository)
    }

    @Provides
    @NewsScope
    fun provideGetNewsUseCase(
        newsRepository: NewsRepository
    ): GetNewsUseCase {
        return GetNewsUseCaseImpl(newsRepository)
    }

    @Provides
    @NewsScope
    fun provideNewsRepository(
        newsService: NewsService,
        newsDao: NewsDao
    ): NewsRepository {
        return NewsRepositoryImpl(newsService, newsDao)
    }

    @Provides
    @NewsScope
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao {
        return newsDatabase.newsDao
    }

    @Provides
    @NewsScope
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @NewsScope
    fun provideNewsService(
        retrofit: Retrofit
    ): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}