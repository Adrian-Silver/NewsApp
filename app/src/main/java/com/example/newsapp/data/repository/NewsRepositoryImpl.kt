package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.NewsPagingSource
import com.example.newsapp.data.remote.SearchNewsPagingSource
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                NewsPagingSource(newsApi = newsApi, sources = sources.joinToString(separator = ","))
            }
        ).flow
    }

    override fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsApi = newsApi,
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun addBookmark(articleEntity: ArticleEntity) {
        newsDao.upsert(articleEntity)
    }

    override suspend fun deleteBookmark(articleEntity: ArticleEntity) {
        newsDao.delete(articleEntity)
    }

    override fun getArticles(): Flow<List<ArticleEntity>> {
        return newsDao.getAllArticles()
    }

    override suspend fun getArticle(url: String): ArticleEntity? {
        return newsDao.getArticle(url = url)
    }
}