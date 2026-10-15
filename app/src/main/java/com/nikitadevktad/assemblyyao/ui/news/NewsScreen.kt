package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikitadevktad.assemblyyao.R
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository

@Composable
fun NewsScreen(
    repository: NewsRepository,
    onNewsClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory(repository))
    val ui = viewModel.ui

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.news),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        OutlinedTextField(
            value = ui.searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            placeholder = { Text(stringResource(R.string.find_a_news_item)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        )
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
            else -> LazyColumn(modifier = Modifier.weight(1f)) {
                items(ui.news, key = { it.id }) { news ->
                    NewsCard(
                        news = news,
                        onClick = { onNewsClick(news.id) },
                        modifier = Modifier.padding(top = 16.dp),
                    )
                }
                item {
                    NewsPagination(
                        currentPage = ui.currentPage,
                        totalPages = ui.totalPages,
                        onPageClick = viewModel::goToPage,
                        modifier = Modifier.padding(vertical = 24.dp),
                    )
                }
            }
        }
    }
}
