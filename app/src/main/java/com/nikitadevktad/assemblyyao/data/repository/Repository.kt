package com.nikitadevktad.assemblyyao.data.repository

import com.nikitadevktad.assemblyyao.data.mapper.toNews
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.remote.retrofit.RetrofitClient

class Repository {

    suspend fun getNews(page: Int): NewsPage {

        val response = RetrofitClient.api.getNews(
            perPage = 5,
            page = page,
        )

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
}
