package com.nikitadevktad.assemblyyao.data.remote

import com.nikitadevktad.assemblyyao.data.mapper.toNews
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.remote.api.ApiService

interface NewsRemoteDataSource {
    suspend fun getNews(page: Int, searchQuery: String): NewsPage
    suspend fun getNews(id: Int): News
}

class RetrofitNewsRemoteDataSource(
    private val api: ApiService,
) : NewsRemoteDataSource {
    override suspend fun getNews(page: Int, searchQuery: String): NewsPage {
        val response = api.getNews(
            perPage = PAGE_SIZE,
            page = page,
            search = searchQuery.ifBlank { null },
        )
        if (!response.isSuccessful) {
            throw NewsRemoteException("Сервер вернул ошибку ${response.code()}")
        }
        val body = response.body() ?: throw NewsRemoteException("Сервер вернул пустой ответ")
        return NewsPage(
            news = body.map { it.toNews() },
            totalPages = response.headers()["X-WP-TotalPages"]?.toIntOrNull() ?: 1,
            totalNews = response.headers()["X-WP-Total"]?.toIntOrNull() ?: body.size,
        )
    }

    override suspend fun getNews(id: Int): News {
        val response = api.getNewsId(id)
        if (!response.isSuccessful) {
            throw NewsRemoteException("Сервер вернул ошибку ${response.code()}")
        }
        return response.body()?.toNews()
            ?: throw NewsRemoteException("Сервер вернул пустой ответ")
    }

    private companion object {
        const val PAGE_SIZE = 5
    }
}

class NewsRemoteException(message: String) : Exception(message)
