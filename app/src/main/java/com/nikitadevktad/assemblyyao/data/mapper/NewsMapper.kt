package com.nikitadevktad.assemblyyao.data.mapper

import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.remote.dto.NewsDto

// Преобразует DTO в модель приложения
fun NewsDto.toNews(): News {
    return News(
        id = id,
        date = date,
        title = title.rendered,
        excerpt = excerpt.rendered,
        content = content.rendered,
        imageUrl = embedded
            ?.featuredMedia
            ?.firstOrNull()
            ?.sourceUrl,
        link = link
    )
}