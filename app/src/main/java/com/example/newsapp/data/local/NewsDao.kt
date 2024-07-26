package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity WHERE url=:url")
    suspend fun getArticle(url: String): ArticleEntity?
}