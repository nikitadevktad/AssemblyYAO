package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsScreen(
    onNewsClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModel = viewModel()
    val ui = viewModel.ui

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Text(
            text = "Новости",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        when {
            ui.isLoading -> {
                Text("Загрузка...")
            }

            ui.error != null -> {
                Text(
                    text = ui.error ?: "Ошибка"
                )
            }

            else -> {
                LazyColumn {
                    items(ui.news) { news ->
                        NewsCard(
                            news = news, onClick = {
                                onNewsClick(news.id)
                            }, modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    item {
                        NewsPagination(
                            currentPage = ui.currentPage,
                            totalPages = ui.totalPages,
                            onPageClick = { page ->
                                viewModel.goToPage(page)
                            },
                            modifier = Modifier.padding(
                                vertical = 24.dp
                            )
                        )
                    }
                }
            }
        }
    }
}