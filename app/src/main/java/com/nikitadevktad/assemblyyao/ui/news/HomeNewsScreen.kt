package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeNewsScreen(
    clickNews: (Int) -> Unit,
    onAllNewsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NewsViewModel = viewModel()
    val ui = viewModel.ui

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Новости",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Все новости >",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onAllNewsClick()
                }
            )
        }
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
                ui.news.forEach { news ->
                    NewsCard(
                        news = news,
                        onClick = {
                            clickNews(news.id)
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}