package com.example.newsapp.presenetation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.domain.usecases.news.NewsUseCases
import com.example.newsapp.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
  private val newsUseCases: NewsUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {

            is DetailsEvent.UpdateBookmark -> {
                viewModelScope.launch {
                    val article = newsUseCases.getArticle(url = event.articleEntity.url)

                    // Check if article exists in bookmarks
                    if (article == null) {
                        addBookmark(articleEntity = event.articleEntity)
                    } else {
                        deleteBookmark(articleEntity = event.articleEntity)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

        }
    }

    private suspend fun addBookmark(articleEntity: ArticleEntity) {
        newsUseCases.addBookmark(articleEntity = articleEntity)
        sideEffect = UIComponent.Toast("Article added to Bookmarks")
    }

    private suspend fun deleteBookmark(articleEntity: ArticleEntity) {
        newsUseCases.deleteBookmark(articleEntity = articleEntity)
        sideEffect = UIComponent.Toast("Article removed from Bookmarks")
    }


}