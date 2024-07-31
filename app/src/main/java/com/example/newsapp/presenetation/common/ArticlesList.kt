package com.example.newsapp.presenetation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.util.Dimens.MediumPadding
import com.example.newsapp.util.Dimens.SmallPadding
import com.example.newsapp.util.Dimens.SmallPadding2


@Composable
fun SavedArticlesList(
    modifier: Modifier = Modifier,
    articles: List<ArticleEntity>,
    onClick: (ArticleEntity) -> Unit
) {

    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding),
        contentPadding = PaddingValues(all = SmallPadding2)
    ) {
        items(
            count = articles.size
        ) {
            articles[it]?.let { article ->
                ArticleCard(articleEntity = article, onClick = { onClick(article) })
            }
        }
    }

}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<ArticleEntity>,
    onClick: (ArticleEntity) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles)
    
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
            contentPadding = PaddingValues(all = SmallPadding2)
        ) {
            items(
                count = articles.itemCount
            ) {
                articles[it]?.let { article ->
                    ArticleCard(articleEntity = article, onClick = { onClick(article) })

                }
            }
            
        }
    }

}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<ArticleEntity>
): Boolean {
    val loadState = articles.loadState

    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }

}

@Composable
fun ShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MediumPadding)
    ) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding)
            )
        }

    }

}