package com.example.newsapp.presenetation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.presenetation.common.ArticlesList
import com.example.newsapp.presenetation.common.SearchBar
import com.example.newsapp.util.Dimens.MediumPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<ArticleEntity>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (ArticleEntity) -> Unit
) {

    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 0))
                    .joinToString(separator = " \ub83D\udFE5 ") { it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding)
            .statusBarsPadding()
    ) {
        Image(
//            painter = painterResource(id = R.drawable.ic_handball_player),
            painter = painterResource(id = R.drawable.ic_medal_gold),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = MediumPadding)
        )

        Spacer(modifier = Modifier.height(MediumPadding))

        SearchBar(
            modifier = Modifier
                .padding(horizontal = MediumPadding),
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = navigateToSearch
        )

        Spacer(modifier = Modifier.height(MediumPadding))

        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MediumPadding)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )

        Spacer(modifier = Modifier.height(MediumPadding))

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding),
            articles = articles,
            onClick = navigateToDetails
        )

    }
}