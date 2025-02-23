package com.example.android_practice_1.retrofit_practice.model

data class TodoUser(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)