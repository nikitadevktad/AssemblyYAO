package com.nikitadevktad.assemblyyao.ui.news

import com.nikitadevktad.assemblyyao.data.model.News

data class NewsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val news: List<News> = emptyList(),
    val totalPages: Int = 1,
    val totalNews: Int = 0,
)
