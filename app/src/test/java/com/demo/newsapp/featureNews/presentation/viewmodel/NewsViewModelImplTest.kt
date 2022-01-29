package com.demo.newsapp.featureNews.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.newsapp.featureNews.domain.model.News
import com.demo.newsapp.featureNews.domain.usecase.FetchNewsUseCase
import com.demo.newsapp.featureNews.domain.usecase.GetNewsUseCase
import com.demo.newsapp.featureNews.presentation.NewsViewModel
import com.demo.newsapp.featureNews.presentation.NewsViewModelImpl
import com.demo.newsapp.featureNews.presentation.util.SchedulerRule
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsViewModelImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val schedulerRule = SchedulerRule()

    @Mock
    private lateinit var fetchNewsUseCase: FetchNewsUseCase

    @Mock
    private lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var viewModel: NewsViewModel

    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        viewModel = NewsViewModelImpl(fetchNewsUseCase, getNewsUseCase)
    }

    @Test
    fun testFetchNews() {
        //Given
        whenever(fetchNewsUseCase.execute()).thenReturn(Completable.complete())

        // Then
        viewModel.fetchNews()
            .test()
            .assertComplete()
            .assertSubscribed()
    }

    @Test
    fun testGetNewsByTimeCreated_shouldReturnCorrectValue() {
        // Given
        val newsList = listOf(
            generateNews().copy(timeCreated = 2L),
            generateNews().copy(timeCreated = 5L),
            generateNews().copy(timeCreated = 1L),
            generateNews().copy(timeCreated = 3L)
        )
        whenever(getNewsUseCase.execute()).thenReturn(Single.just(newsList))

        // Then
        viewModel.getNewsByTimeCreated()
            .test()
            .assertValues(newsList.sortedBy { it.timeCreated })
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun testGetNewsByRank_shouldReturnCorrectValue() {
        // Given
        val newsList = listOf(
            generateNews().copy(rank = 2, timeCreated = 22L),
            generateNews().copy(rank = 1, timeCreated = 33L),
            generateNews().copy(rank = 3, timeCreated = 44L),
            generateNews().copy(rank = 3, timeCreated = 11L),
        )
        whenever(getNewsUseCase.execute()).thenReturn(Single.just(newsList))

        // Then
        viewModel.getNewsByRank()
            .test()
            .assertValues(newsList.sortedWith(compareBy({it.rank}, {it.timeCreated})))
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
    }

    private fun generateNews(): News {
        return News("id", "title", "desc", "bannerUrl", 1, 1)
    }

    @After
    fun tearDown() {
        closeable.close()
    }
}