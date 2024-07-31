package com.example.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import coil.network.HttpException
import com.example.newsapp.data.local.ArticleEntity
import java.io.IOException

class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery:String,
    private val sources: String
): PagingSource<Int, ArticleEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        return try {
            val pageNumber = params.key ?: 1

            val newsResponse = newsApi.searchNews(searchQuery = searchQuery, sources = sources, page = pageNumber)

            val articles = newsResponse.articles.distinctBy { it.title } // Remove duplicates

            LoadResult.Page(
                data = articles,
                nextKey = if (newsResponse.articles.size == newsResponse.totalResults) null else pageNumber + 1,
//                prevKey = if (pageNumber > 0) pageNumber - 1 else null
                prevKey = null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)

        }
    }
}