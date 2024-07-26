package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources: List<String>): Flow<PagingData<ArticleEntity>>

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<ArticleEntity>>

    suspend fun addBookmark(articleEntity: ArticleEntity)

    suspend fun deleteBookmark(articleEntity: ArticleEntity)

    fun getArticles(): Flow<List<ArticleEntity>>

    suspend fun getArticle(url: String): ArticleEntity?
}