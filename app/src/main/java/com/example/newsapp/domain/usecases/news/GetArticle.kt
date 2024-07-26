package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.local.NewsDao

class GetArticle(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String): ArticleEntity? {
        return newsDao.getArticle(url = url)
    }
}