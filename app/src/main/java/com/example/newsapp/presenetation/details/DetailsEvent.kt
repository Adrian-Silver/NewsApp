package com.example.newsapp.presenetation.details

import com.example.newsapp.data.local.ArticleEntity

sealed class DetailsEvent {

    data class UpdateBookmark(val articleEntity: ArticleEntity): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()

}