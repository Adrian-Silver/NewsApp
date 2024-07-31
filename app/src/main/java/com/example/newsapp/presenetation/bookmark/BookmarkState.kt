package com.example.newsapp.presenetation.bookmark

import com.example.newsapp.data.local.ArticleEntity

data class BookmarkState(
    val articlesList: List<ArticleEntity> = emptyList()
)
