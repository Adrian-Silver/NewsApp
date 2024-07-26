package com.example.newsapp.data.remote.dto

import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<ArticleEntity>,
    val status: String,
    val totalResults: Int
)