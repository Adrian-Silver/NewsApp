package com.example.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
//    private val newsRepositoryImpl: NewsRepositoryImpl
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<ArticleEntity>> {
//        return newsRepositoryImpl.getNews(sources = sources)
        return newsRepository.getNews(sources = sources)
    }
}