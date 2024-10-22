package com.example.android_practice_1.pagination.paging_components

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_practice_1.mvvm.model.Article
import com.example.android_practice_1.pagination.networking.NewsApiService

class DataPagingSource(private val apiService: NewsApiService) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1 // Start from the first page if key is null
        return try {
            val response = apiService.getNews(page, params.loadSize)
            Log.d("check_response","${response}")
            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("check_PagingSource_res", "Error loading data: ${e.localizedMessage}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }
}
