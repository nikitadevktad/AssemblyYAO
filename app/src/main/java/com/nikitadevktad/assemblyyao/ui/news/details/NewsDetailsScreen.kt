package com.nikitadevktad.assemblyyao.ui.news.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.ui.news.formatDate
import com.nikitadevktad.assemblyyao.R

// Загрузка данных для одной новости
@Composable
fun NewsDetailsScreen(
    newsId: Int?,
) {
    val viewModel: NewsDetailsViewModel = viewModel()
    val ui = viewModel.ui

    LaunchedEffect(newsId) {
        viewModel.loadNews(newsId!!)
    }
    when {
        ui.isLoading -> {
            Text(stringResource(R.string.download))
        }

        ui.error != null -> {
            Text(ui.error ?: stringResource(R.string.error))
        }

        ui.news != null -> {
            NewsDetailsContent(
                news = ui.news
            )
        }
    }
}

// Отрисовка для одной новости
@Composable
fun NewsDetailsContent(
    news: News,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = news.imageUrl,
            contentDescription = news.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 24.dp
            )
        ) {

            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = formatDate(news.date),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = news.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = HtmlCompat.fromHtml(
                    news.content,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString(),
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 26.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Preview
@Composable
fun ShowNewsDetailsScreen() {
    NewsDetailsContent(
        news = News(
            id = 1,
            date = "2026-10-07T12:00:00",
            title = "Ассамблея народов Ярославской области провела встречу..",
            excerpt = "",
            content = """
                ...
                ...
                ...
                ...
                ...
            """.trimIndent(),
            imageUrl = null,
            link = "",
        )
    )
}