package com.example.android_practice_1.nested_menu.helper

import com.example.android_practice_1.nested_menu.model.Category
import com.google.gson.Gson

object JsonParser {
    fun parseCategories(jsonString: String): List<Category> {
        val response = Gson().fromJson(jsonString, CategoryResponse::class.java)
        return response.categoryList.parent
    }
}

data class CategoryResponse(
    val success: Boolean,
    val message: String,
    val categoryList: CategoryList,
    val eTag: String
)

data class CategoryList(
    val parent: List<Category>
)