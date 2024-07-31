package com.example.newsapp.presenetation.common

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.domain.model.Source
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.Dimens.ArticleCardSize
import com.example.newsapp.util.Dimens.SmallIconSize
import com.example.newsapp.util.Dimens.SmallPadding

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    articleEntity: ArticleEntity,
    onClick:(() -> Unit)? = null
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.clickable {
            onClick?.invoke()
        },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest
                .Builder(context)
                .data(articleEntity.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
            )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = SmallPadding)
                .height(ArticleCardSize)
        ) {
            Text(
                text = articleEntity.title,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = articleEntity.source.name,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                    )
                Spacer(modifier = Modifier.width(SmallPadding))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(SmallPadding))
                Text(
                    text = articleEntity.publishedAt,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewsAppTheme(dynamicColor = false) {
        ArticleCard(
            articleEntity = ArticleEntity(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours ago",
                source = Source(id = "", name = "Kwetu News"),
                title = "The Team Kenya Olympic team awaits its most infamous events in the track categories",
                url = "",
                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
            )
        )

    }
}
