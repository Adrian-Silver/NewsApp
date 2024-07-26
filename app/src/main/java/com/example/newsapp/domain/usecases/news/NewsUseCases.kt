package com.example.newsapp.domain.usecases.news

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val addBookmark: AddBookmark,
    val deleteBookmark: DeleteBookmark,
    val getArticle: GetArticle,
    val getArticles: GetArticles,
)