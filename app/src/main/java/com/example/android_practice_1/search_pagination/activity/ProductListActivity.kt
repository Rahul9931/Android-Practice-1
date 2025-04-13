package com.example.android_practice_1.search_pagination.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.R
import com.example.android_practice_1.search_pagination.adapter.ProductAdapter
import com.example.android_practice_1.search_pagination.model.Product
import com.example.android_practice_1.search_pagination.widget.CustomToolbar

class ProductListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var toolbar: CustomToolbar
    private lateinit var loadingProgressBar: ProgressBar

    private var currentPage = 1
    private var isLoading = false
    private var isSearching = false
    private var currentQuery = ""
    private val pageSize = 10
    private val allProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerView = findViewById(R.id.productRecyclerView)
        toolbar = findViewById(R.id.toolbar)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        setupToolbar()
        setupRecyclerView()
        loadProducts()
    }

    private fun setupToolbar() {
        toolbar.setTitle("Product List")

        toolbar.onBackPressed = {
            if (isSearching) {
                toolbar.toggleSearch()
                currentQuery = ""
                loadProducts(reset = true)
            } else {
                finish()
            }
        }

        toolbar.onSearchStateChanged = { searching ->
            isSearching = searching
            if (!searching) {
                currentQuery = ""
                loadProducts(reset = true)
            }
        }

        toolbar.onQueryTextChanged = { query ->
            currentQuery = query
            loadProducts(reset = true)
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(
            emptyList(),
            onEditClick = { product ->
                Log.d("ProductList", "Edit clicked for product: ${product.title}")
                Toast.makeText(this, "Edit: ${product.title}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { product ->
                deleteProduct(product)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    loadProducts()
                }
            }
        })
    }

    private fun loadProducts(reset: Boolean = false) {
        if (isLoading) return

        if (reset) {
            currentPage = 1
        }

        isLoading = true
        loadingProgressBar.visibility = View.VISIBLE

        // Simulate API call
        recyclerView.postDelayed({
            val products = if (currentQuery.isEmpty()) {
                generateFakeProducts(currentPage, pageSize)
            } else {
                searchProducts(currentQuery)
            }

            if (reset) {
                allProducts.clear()
                allProducts.addAll(generateFakeProducts(1, currentPage * pageSize))
                adapter = ProductAdapter(
                    products,
                    onEditClick = { product ->
                        Log.d("ProductList", "Edit clicked for product: ${product.title}")
                    },
                    onDeleteClick = { product ->
                        deleteProduct(product)
                    }
                )
                recyclerView.adapter = adapter
            } else {
                allProducts.addAll(products)
                adapter.addProducts(products)
            }

            if (products.isNotEmpty()) {
                currentPage++
            }

            isLoading = false
            loadingProgressBar.visibility = View.GONE
        }, 500)
    }

    private fun generateFakeProducts(page: Int, pageSize: Int): List<Product> {
        val products = mutableListOf<Product>()
        val start = (page - 1) * pageSize
        val end = start + pageSize

        for (i in start until end) {
            products.add(
                Product(
                    id = i,
                    title = "Product $i",
                    description = "Description for product $i",
                    price = (10..100).random().toDouble(),
                    imageUrl = "https://picsum.photos/200/300?image=$i"
                )
            )
        }
        return products
    }

    private fun searchProducts(query: String): List<Product> {
        return allProducts.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }

    private fun deleteProduct(product: Product) {
        Log.d("check_product_dlt","dlt product = $product")
        val position = allProducts.indexOfFirst { it.id == product.id }
        if (position != -1) {
            allProducts.removeAt(position)
            adapter.removeProduct(product)
            Toast.makeText(this, "${product.title} deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (isSearching) {
            toolbar.toggleSearch()
            currentQuery = ""
            loadProducts(reset = true)
        } else {
            super.onBackPressed()
        }
    }
}