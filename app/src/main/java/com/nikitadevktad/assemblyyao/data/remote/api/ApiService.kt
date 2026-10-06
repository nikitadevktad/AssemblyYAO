package com.nikitadevktad.assemblyyao.data.remote.api

import com.nikitadevktad.assemblyyao.data.remote.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
// Описание запросов к api
interface ApiService {
    @GET("wp-json/wp/v2/posts")
    suspend fun getNews(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("_embed") embed: Boolean = true,
    ): Response<List<NewsDto>>
}