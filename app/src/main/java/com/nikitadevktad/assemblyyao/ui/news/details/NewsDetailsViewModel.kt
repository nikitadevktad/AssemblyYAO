package com.nikitadevktad.assemblyyao.ui.news.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    private val repository: NewsRepository,
) : ViewModel() {
    private var _ui by mutableStateOf(
        NewsDetailUiState()
    )
    val ui: NewsDetailUiState
        get() = _ui

    fun loadNews(id: Int) {
        viewModelScope.launch {
            _ui = _ui.copy(
                isLoading = true,
                error = null,
            )
            try {
                val result = repository.getNews(id)
                _ui = _ui.copy(
                    news = result.data,
                    isFromCache = result.isFromCache,
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _ui = _ui.copy(
                    error = exception.message,
                    isFromCache = false,
                )
            } finally {
                _ui = _ui.copy(
                    isLoading = false,
                )
            }
        }
    }

    class Factory(
        private val repository: NewsRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(NewsDetailsViewModel::class.java))
            return NewsDetailsViewModel(repository) as T
        }
    }
}
