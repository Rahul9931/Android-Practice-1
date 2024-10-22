package com.example.android_practice_1.pagination.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityPagingPracticeBinding
import com.example.android_practice_1.mvvm.model.Article
import com.example.android_practice_1.mvvm.model.News
import com.example.android_practice_1.pagination.adapter.DataPagingAdapter
import com.example.android_practice_1.pagination.adapter.NewsAdapter
import com.example.android_practice_1.pagination.networking.RetrofitHelper
import com.example.android_practice_1.pagination.paging_components.DataPagingSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagingPracticeActivity : AppCompatActivity() {

    private val binding : ActivityPagingPracticeBinding by lazy {
        ActivityPagingPracticeBinding.inflate(layoutInflater)
    }
    private var currentPage = 1
    private var isLoading = false
    private var totalPages = 1
    //lateinit var newsAdapter : NewsAdapter
    lateinit var newsAdapter2 : DataPagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setAdapter(mutableListOf())
        binding.btnCalApi.setOnClickListener {
            //loadArticles(1)
            setDataPagingAdapter()

            // Set up the paging data
            val apiService = RetrofitHelper.apiService
            Log.d("check_apiservice","apiservice")
            lifecycleScope.launch {
                Log.d("MainActivity", "Starting data loading...")
                Pager(PagingConfig(pageSize = 10)) {
                    DataPagingSource(apiService)
                }.flow.collectLatest {
                    Log.d("check_it","${it}")
                    newsAdapter2.submitData(it)
                }
            }
        }

//        binding.rvPaging.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val layoutManager = binding.rvPaging.layoutManager as LinearLayoutManager
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                Log.d("check_scrool","firstvisibleitemposition = ${firstVisibleItemPosition}\nvisibleItemCount = ${visibleItemCount}\ntotalItemCount = ${totalItemCount}\ncurrentPage = ${currentPage}\ntotalPages = ${totalPages}")
//                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount && currentPage < totalPages) {
//                    currentPage++
//                    loadArticles(currentPage)
//                }
//
//            }
//        })

    }

//    private fun loadArticles(page:Int) {
//        RetrofitHelper.apiService.getNews(page,5).enqueue(object : Callback<News>{
//            override fun onResponse(p0: Call<News>, response: Response<News>) {
//                Log.d("check_res","${response.body()}")
//                response.body()?.articles.let {
//                    newsAdapter.addArticles(it as MutableList<Article>)
//                    totalPages = response.body()?.totalResults!!
//                }
//
//            }
//
//            override fun onFailure(p0: Call<News>, t: Throwable) {
//                Log.d("check_res","${t.message}")
//            }
//
//        })
//    }

//    private fun setAdapter(articles: List<Article>?) {
//        binding.rvPaging.layoutManager = LinearLayoutManager(this)
//        newsAdapter = NewsAdapter(articles as MutableList<Article>)
//        binding.rvPaging.adapter = newsAdapter
//    }

    private fun setDataPagingAdapter() {
        binding.rvPaging.layoutManager = LinearLayoutManager(this)
        newsAdapter2 = DataPagingAdapter()
        binding.rvPaging.adapter = newsAdapter2
        Log.d("check_adapter","${newsAdapter2}")
    }


}