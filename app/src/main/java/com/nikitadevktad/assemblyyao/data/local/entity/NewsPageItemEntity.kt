package com.nikitadevktad.assemblyyao.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "news_page_items",
    primaryKeys = ["searchQuery", "page", "newsId"],
    indices = [
        Index(value = ["searchQuery", "page"]),
        Index(value = ["newsId"]),
    ],
)
data class NewsPageItemEntity(
    val searchQuery: String,
    val page: Int,
    val newsId: Int,
    val position: Int,
)
