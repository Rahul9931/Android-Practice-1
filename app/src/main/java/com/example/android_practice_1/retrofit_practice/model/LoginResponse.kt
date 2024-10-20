package com.example.android_practice_1.retrofit_practice.model

data class LoginResponse(
    val currency: String,
    val error: Int,
    val language: String,
    val status: String,
    val walkthrough_status: String,
    val wk_token: String
)