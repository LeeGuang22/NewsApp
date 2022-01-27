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
import com.demo.newsapp.featureNews.presentation.NewsViewModel
import com.demo.newsapp.featureNews.presentation.NewsViewModelFactory
import com.demo.newsapp.featureNews.presentation.NewsViewModelImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    fun provideNewsViewModel(
        fetchNewsUseCase: FetchNewsUseCase,
        getNewsUseCase: GetNewsUseCase
    ): NewsViewModel {
        return NewsViewModelImpl(fetchNewsUseCase, getNewsUseCase)
    }

    @Provides
    @NewsScope
    fun provideViewModelFactory(
        fetchNewsUseCase: FetchNewsUseCase,
        getNewsUseCase: GetNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(fetchNewsUseCase, getNewsUseCase)
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
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @NewsScope
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @NewsScope
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val newRequest = chain.request().newBuilder().build()
                proceed(newRequest)
            }
        }
    }

    @Provides
    @NewsScope
    fun provideNewsService(
        retrofit: Retrofit
    ): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}