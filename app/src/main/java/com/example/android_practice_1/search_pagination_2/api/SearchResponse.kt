package com.example.android_practice_1.search_pagination_2.api

import com.example.android_practice_1.search_pagination_2.model.Product

data class SearchResponse(
    val success: Boolean,
    val responseCode: Int,
    val message: String,
    val itemsPerPage: Int,
    val customerId: Int,
    val userId: Int,
    val cartCount: Int,
    val WishlistCount: Int,
    val offset: Int,
    val tcount: Int,
    val products: List<Product>,
    val wishlist: List<Int>
)