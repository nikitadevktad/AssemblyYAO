package com.nikitadevktad.assemblyyao.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "news_pages",
    primaryKeys = ["searchQuery", "page"],
)
data class NewsPageEntity(
    val searchQuery: String,
    val page: Int,
    val totalPages: Int,
    val totalNews: Int,
    val cachedAt: Long,
)
