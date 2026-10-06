package com.nikitadevktad.assemblyyao.data.model

data class NewsPage(
    val news: List<News>,
    val totalPages: Int,
    val totalNews: Int
)