package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.local.NewsDao

class DeleteBookmark(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(articleEntity: ArticleEntity) {
        newsDao.delete(article = articleEntity)
    }
}