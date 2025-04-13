package com.example.android_practice_1.search_pagination.api

import com.example.android_practice_1.search_pagination.model.Product

object FakeProductApi {
    private val products = mutableListOf<Product>()

    init {
        // Generate some fake products
        for (i in 1..100) {
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
    }

    fun getProducts(page: Int, pageSize: Int = 10): List<Product> {
        val start = (page - 1) * pageSize
        if (start >= products.size) return emptyList()
        val end = minOf(start + pageSize, products.size)
        return products.subList(start, end)
    }

    fun searchProducts(query: String, page: Int, pageSize: Int = 10): List<Product> {
        val filtered = products.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
        val start = (page - 1) * pageSize
        if (start >= filtered.size) return emptyList()
        val end = minOf(start + pageSize, filtered.size)
        return filtered.subList(start, end)
    }

    fun deleteProduct(id: Int): Boolean {
        val productToRemove = products.find { it.id == id }
        return if (productToRemove != null) {
            products.remove(productToRemove)
            true
        } else {
            false
        }
    }
}