package com.nikitadevktad.assemblyyao.data.model

// Модель страницы новостей с данными о пагинации
data class NewsPage(
    val news: List<News>,
    val totalPages: Int,
    val totalNews: Int
)