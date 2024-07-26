package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.local.NewsDao
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<ArticleEntity>> {
        return newsDao.getAllArticles()
    }
}