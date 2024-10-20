package com.example.android_practice_1.mvvm.model

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)