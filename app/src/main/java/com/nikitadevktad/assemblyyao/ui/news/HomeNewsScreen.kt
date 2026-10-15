package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikitadevktad.assemblyyao.R
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository


@Composable
fun HomeNewsScreen(
    repository: NewsRepository,
    searchQuery: String,
    clickNews: (Int) -> Unit,
    onAllNewsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory(repository))
    val ui = viewModel.ui

    LaunchedEffect(searchQuery) {
        viewModel.updateSearchQuery(searchQuery)
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.news),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.all_news),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onAllNewsClick()
                }
            )
        }
        if (ui.isFromCache) {
            OfflineDataBanner(modifier = Modifier.padding(top = 12.dp))
        }
        when {
            ui.isLoading -> CircularProgressIndicator(modifier = Modifier.padding(24.dp))
            ui.error != null -> NewsError(
                message = ui.error,
                onRetry = viewModel::retry,
            )
            ui.news.isEmpty() -> Text(
                text = stringResource(R.string.no_news_found),
                modifier = Modifier.padding(vertical = 24.dp),
            )
            else -> ui.news.forEach { news ->
                NewsCard(
                    news = news,
                    onClick = { clickNews(news.id) },
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
        }
    }
}
