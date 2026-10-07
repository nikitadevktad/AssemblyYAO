package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nikitadevktad.assemblyyao.data.model.News
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewsCard(
    news: News,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            Box {
                AsyncImage(
                    model = news.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop,
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(y = 20.dp),
                    shape = RoundedCornerShape(
                        topStart = 24.dp, topEnd = 24.dp, bottomEnd = 24.dp
                    ),
                ) {
                    Text(
                        text = formatDate(news.date), modifier = Modifier.padding(
                            horizontal = 24.dp, vertical = 12.dp
                        )
                    )
                }
            }

            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp, end = 24.dp, top = 36.dp
                    ),
                textAlign = TextAlign.Start,
            )

            Text(
                text = "Подробнее",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = 24.dp, top = 16.dp, bottom = 24.dp
                )
            )
        }
    }
}

fun formatDate(
    date: String,
): String {
    val parsedDate = LocalDateTime.parse(date)

    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale("ru")
    )

    return parsedDate.format(formatter)
}