package com.nikitadevktad.assemblyyao.data.model

// Модель новости
data class News(
    val id: Int,
    val date: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val imageUrl: String?,
    val link: String
)
