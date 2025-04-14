package com.example.android_practice_1.search_pagination_2.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivitySearchBinding
import com.example.android_practice_1.search_pagination_2.adapter.ProductAdapter
import com.example.android_practice_1.search_pagination_2.api.ApiClient
import com.example.android_practice_1.search_pagination_2.api.SearchRequest
import com.example.android_practice_1.search_pagination_2.api.SearchResponse
import com.example.android_practice_1.search_pagination_2.utils.PaginationScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ProductAdapter
    private var isLoading = false
    private var isLastPage = false
    private var currentOffset = 0
    private var currentQuery = ""
    private var totalItems = 0
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()

    }
    private fun setupRecyclerView() {
        adapter = ProductAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(
            binding.recyclerView.layoutManager as LinearLayoutManager
        ) {
            override fun isLastPage(): Boolean = isLastPage
            override fun isLoading(): Boolean = isLoading
            override fun loadMoreItems() {
                if (!isLastPage && !isLoading && currentQuery.isNotEmpty()) {
                    loadNextPage()
                }
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    // Cancel previous search request if it exists
                    searchJob?.cancel()

                    if (query.length >= 2) { // Only search when at least 2 characters are entered
                        currentQuery = query
                        currentOffset = 0
                        isLastPage = false
                        totalItems = 0
                        adapter.clearProducts()

                        // Add debounce to avoid too many API calls while typing
                        searchJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(500) // 500ms debounce time
                            if (query == currentQuery) { // Ensure the query hasn't changed during debounce
                                searchProducts(query, currentOffset)
                            }
                        }
                    } else if (query.isEmpty()) {
                        // Clear results if search query is empty
                        currentQuery = ""
                        adapter.clearProducts()
                    }
                }
                return true
            }
        })
    }

    private fun loadNextPage() {
        if (currentOffset + PaginationScrollListener.PAGE_SIZE < totalItems) {
            currentOffset += PaginationScrollListener.PAGE_SIZE
            searchProducts(currentQuery, currentOffset)
        } else {
            isLastPage = true
        }
    }

    private fun searchProducts(query: String, offset: Int) {
        if (isLoading) return

        isLoading = true
        if (offset == 0) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            adapter.addLoadingFooter()
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.searchProducts(SearchRequest(offset, query))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            handleSearchResponse(it)
                        }
                    } else {
                        showError("Failed to load products")
                        resetPaginationState()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Network error: ${e.message}")
                    resetPaginationState()
                }
            }
        }
    }

    private fun handleSearchResponse(response: SearchResponse) {
        isLoading = false
        binding.progressBar.visibility = View.GONE
        adapter.removeLoadingFooter()

        totalItems = response.tcount

        if (response.products.isEmpty()) {
            isLastPage = true
            if (currentOffset == 0) {
                showEmptyState()
            }
            return
        }

        adapter.addProducts(response.products)
        isLastPage = currentOffset + response.products.size >= totalItems
    }

    private fun resetPaginationState() {
        isLoading = false
        binding.progressBar.visibility = View.GONE
        adapter.removeLoadingFooter()
    }

    private fun showEmptyState() {
        Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}