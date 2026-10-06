package com.nikitadevktad.assemblyyao.data.remote.retrofit

import com.nikitadevktad.assemblyyao.data.remote.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Логирование запросов и ответов
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // Настройка HTTP клиента
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
    // Настройка ретрофита и создание ApiService
    val api: ApiService = Retrofit.Builder()
        .baseUrl("https://ассамблея76.рф/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)
}