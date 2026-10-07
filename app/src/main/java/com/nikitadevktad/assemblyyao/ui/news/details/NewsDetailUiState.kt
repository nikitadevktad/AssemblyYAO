package com.nikitadevktad.assemblyyao.ui.news.details

import com.nikitadevktad.assemblyyao.data.model.News

data class NewsDetailUiState(
    val news: News? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)