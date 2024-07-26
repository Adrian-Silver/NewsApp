package com.example.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.repository.NewsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepositoryImpl: NewsRepositoryImpl
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<ArticleEntity>> {
        return newsRepositoryImpl.getNews(sources = sources)
    }
}