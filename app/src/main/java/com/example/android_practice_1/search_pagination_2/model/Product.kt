package com.example.android_practice_1.search_pagination_2.model

data class Product(
    val templateId: Int,
    val name: String,
    val priceUnit: String,
    val priceReduce: String,
    val productId: Int,
    val productCount: Int,
    val description: String,
    val thumbNail: String,
    val ribbon: Ribbon
)

data class Ribbon(
    val ribbon_message: String,
    val position: String,
    val text_color: String,
    val bg_color: String
)