package com.example.newsapp.presenetation.search

import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleEntity
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<ArticleEntity>>? = null
)
