package com.nikitadevktad.assemblyyao.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val imageUrl: String?,
    val link: String,
    val cachedAt: Long,
)
