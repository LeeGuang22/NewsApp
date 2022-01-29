package com.demo.newsapp.featureNews.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.demo.newsapp.R
import com.demo.newsapp.databinding.ActivityNewsBinding
import com.demo.newsapp.di.NewsInjector
import com.demo.newsapp.featureNews.domain.model.News
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityNewsBinding

    private lateinit var adapter: NewsAdapter

    private lateinit var compositeDisposable: CompositeDisposable

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        setupViewBinding()
        fetchInitialData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
    // endregion

    // region sort news
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionRecent -> {
                sortNews(
                    sortedNews = viewModel.getNewsByTimeCreated(),
                    message = "Sorted by time created"
                )
                true
            }
            R.id.actionPopular -> {
                sortNews(
                    sortedNews = viewModel.getNewsByRank(),
                    message = "Sorted by rank"
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortNews(sortedNews: Single<List<News>>, message: String) {
        compositeDisposable.add(
            sortedNews.subscribe({
                updateNews(it, message)
            }) {
                logError("Sort Error", it.localizedMessage)
            }
        )
    }

    private fun updateNews(news: List<News>, message: String) {
        adapter.updateNews(news)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun logError(tag: String, errMsg: String) {
        Log.d(tag, errMsg)
    }
    // endregion

    // region initial setup
    private fun initDagger() {
        NewsInjector.newsComponent.inject(this)
    }

    private fun setupViewBinding() {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    private fun fetchInitialData() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            viewModel.fetchNews()
                .andThen {
                    insertNews()
                }.subscribe(
                    {},
                    {
                        logError("Fetch Error", it.localizedMessage)
                    }
                )
        )
    }

    private fun insertNews() {
        viewModel.getNewsByTimeCreated().subscribe { news ->
            setupRecyclerView(news)
        }
    }

    private fun setupRecyclerView(news: List<News>) {
        adapter = NewsAdapter().apply {
            items = news
        }
        binding.rvNews.adapter = adapter
    }
    // endregion
}