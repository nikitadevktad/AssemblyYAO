package com.nikitadevktad.assemblyyao.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsDto(
    val id: Int,
    val date: String,
    val link: String,
    val title: RenderedDto,
    val excerpt: RenderedDto,
    val content: RenderedDto,

    @SerializedName("_embedded")
    val embedded: EmbeddedDto?
)

data class RenderedDto(
    val rendered: String
)

data class EmbeddedDto(
    @SerializedName("wp:featuredmedia")
    val featuredMedia: List<FeaturedMediaDto>?
)

data class FeaturedMediaDto(
    @SerializedName("source_url")
    val sourceUrl: String
)
