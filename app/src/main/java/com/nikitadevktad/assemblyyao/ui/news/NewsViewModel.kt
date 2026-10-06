package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadevktad.assemblyyao.data.repository.Repository
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel(){
    private val repository = Repository()

    private var _ui by mutableStateOf(
        NewsUiState()
    )
    val ui: NewsUiState
        get() = _ui

    init{
        loadNews()
    }
    fun newsPage(){
        _ui = _ui.copy(
            currentPage = _ui.currentPage +1
        )
        loadNews()
    }
    fun backPage(){
        if(_ui.currentPage > 1){
            _ui = _ui.copy(
                currentPage = _ui.currentPage - 1
            )
            loadNews()
        }
    }
    fun goToPage(page: Int){
        if (page in 1..ui.totalPages){
            _ui = _ui.copy(
                currentPage = page
            )
            loadNews()
        }
    }
    fun loadNews(){
        viewModelScope.launch {
            _ui = _ui.copy(
                isLoading = true,
                error = null,
            )
            try {
                val newsPage = repository.getNews(
                    page = _ui.currentPage
                )

                _ui = _ui.copy(
                    news = newsPage.news,
                    totalPages = newsPage.totalPages,
                    totalNews = newsPage.totalNews
                )
            }
            catch (e: Exception){
                _ui = _ui.copy(
                    error = e.message
                )
            }
            finally {
                _ui = _ui.copy(
                    isLoading = false,
                )
            }
        }
    }
}
