package com.demo.newsapp.featureNews.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.demo.newsapp.R
import com.demo.newsapp.databinding.ActivityNewsBinding
import com.demo.newsapp.di.NewsInjector
import com.demo.newsapp.featureNews.domain.model.News
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityNewsBinding

    private lateinit var adapter: NewsAdapter

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        setupView()
        fetchInitialData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.actionRecent -> {
                viewModel.getNewsByTimeCreated()
                    .subscribe { news ->
                        adapter.updateNews(news)
                    }
                Snackbar.make(binding.root, "Sorted by time created", Snackbar.LENGTH_SHORT).show()
                true
            }
            R.id.actionPopular -> {
                viewModel.getNewsByRank()
                    .subscribe { news ->
                        adapter.updateNews(news)
                    }
                Snackbar.make(binding.root, "Sorted by rank ", Snackbar.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initDagger() {
        NewsInjector.newsComponent.inject(this)
    }

    private fun setupView() {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    private fun fetchInitialData() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            viewModel.fetchNews()
                .andThen {
                    viewModel.getNewsByTimeCreated().subscribe { news ->
                        setupRecyclerView(news)
                    }
                }.subscribe {}
        )
    }

    private fun setupRecyclerView(news: List<News>) {
        adapter = NewsAdapter().apply {
            items = news
        }
        binding.rvNews.adapter = adapter
    }
}