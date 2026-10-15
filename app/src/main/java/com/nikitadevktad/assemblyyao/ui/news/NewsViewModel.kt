package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository,
) : ViewModel() {
    private var _ui by mutableStateOf(NewsUiState())
    val ui: NewsUiState get() = _ui
    private var loadJob: Job? = null

    init {
        loadNews()
    }

    fun goToPage(page: Int) {
        if (page in 1..ui.totalPages && page != ui.currentPage) {
            _ui = _ui.copy(currentPage = page)
            loadNews()
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        if (searchQuery == _ui.searchQuery) return
        _ui = _ui.copy(searchQuery = searchQuery, currentPage = 1)
        loadNews(SEARCH_DEBOUNCE_MILLIS)
    }

    fun retry() = loadNews()

    private fun loadNews(delayMillis: Long = 0) {
        // Новый запрос отменяет debounce или загрузку для предыдущего состояния экрана.
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (delayMillis > 0) delay(delayMillis)
            _ui = _ui.copy(isLoading = true, error = null)
            try {
                val result = repository.getNews(
                    page = _ui.currentPage,
                    searchQuery = _ui.searchQuery,
                )
                _ui = _ui.copy(
                    news = result.data.news,
                    totalPages = result.data.totalPages,
                    totalNews = result.data.totalNews,
                    isFromCache = result.isFromCache,
                    isLoading = false,
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _ui = _ui.copy(
                    error = exception.message,
                    isFromCache = false,
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
            require(modelClass.isAssignableFrom(NewsViewModel::class.java))
            return NewsViewModel(repository) as T
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 350L
    }
}
