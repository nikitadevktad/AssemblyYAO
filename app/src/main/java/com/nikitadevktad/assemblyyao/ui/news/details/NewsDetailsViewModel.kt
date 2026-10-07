package com.nikitadevktad.assemblyyao.ui.news.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.assemblyyao.data.repository.Repository
import kotlinx.coroutines.launch

class NewsDetailsViewModel : ViewModel() {
    private var _ui by mutableStateOf(
        NewsDetailUiState()
    )
    val ui: NewsDetailUiState
        get() = _ui

    val repository = Repository()

    fun loadNews(id: Int) {
        viewModelScope.launch {
            _ui = _ui.copy(
                isLoading = true,
                error = null,
            )
            try {
                val news = repository.getNewsId(
                    id = id
                )
                _ui = _ui.copy(
                    news = news
                )
            } catch (e: Exception) {
                _ui = _ui.copy(
                    error = e.message
                )
            } finally {
                _ui = _ui.copy(
                    isLoading = false,
                )
            }
        }
    }
}