package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.local.converters.NewsTypeConverter

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract val newsDao: NewsDao
}