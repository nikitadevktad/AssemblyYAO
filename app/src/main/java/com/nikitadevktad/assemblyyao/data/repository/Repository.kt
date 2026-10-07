package com.nikitadevktad.assemblyyao.data.repository

import com.nikitadevktad.assemblyyao.data.mapper.toNews
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.remote.retrofit.RetrofitClient

class Repository {
    suspend fun getNews(page: Int): NewsPage {
        // Запрос пяти новостей для одной страницы
        val response = RetrofitClient.api.getNews(
            perPage = 5,
            page = page,
        )
        // Проверка на успешное выполнение запроса на получение списка новостей
        if (response.isSuccessful) {

            val body = response.body()
                ?: throw Exception("Пустой ответ")

            val totalPages = response.headers()["X-WP-TotalPages"]
                ?.toIntOrNull()
                ?: 1

            val totalNews = response.headers()["X-WP-Total"]
                ?.toIntOrNull()
                ?: body.size

            return NewsPage(
                news = body.map { it.toNews() },
                totalPages = totalPages,
                totalNews = totalNews
            )

        } else {
            throw Exception("Ошибка: ${response.code()}")
        }
    }
    suspend fun getNewsId(id: Int): News {
        val response = RetrofitClient.api.getNewsId(
            id = id,
        )
        // Проверка на успешное выполнение запроса на получение одной новости
        if (response.isSuccessful) {
            val body = response.body()
                ?: throw Exception("Пустой ответ")

            return body.toNews()
        } else {
            throw Exception("Ошибка ${response.code()}")
        }
    }
}
