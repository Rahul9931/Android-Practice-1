package com.example.android_practice_1.pagination.paging_components

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_practice_1.mvvm.model.Article
import com.example.android_practice_1.pagination.networking.NewsApiService
import retrofit2.await

class DataPagingSource(private val apiService: NewsApiService) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1  // Start at page 1 if no page is specified
        return try {
            Log.d("check_parameter","page = ${page}\nload size = ${params.loadSize}")
            val response = apiService.getNews(page, 10).await()
            Log.d("check_res", "${response}")
            // Handle successful response
            val data = response.articles // Assuming your API returns a 'data' field
            val nextPage = if (data.isNotEmpty()) page + 1 else null
            if (response.articles.isNotEmpty()) {
                val articles = response.articles
                LoadResult.Page(
                    data = articles,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (articles.isEmpty()) null else page + 1
                )
            } else {
                Log.d("check_res_error","${response}")
                LoadResult.Error(Exception(response.status))
            }
        } catch (e: Exception) {
            Log.d("check_res_error","${e.localizedMessage}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
