package com.nikitadevktad.assemblyyao.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("title")
    val title: RenderedDto,

    @field:SerializedName("excerpt")
    val excerpt: RenderedDto,

    @field:SerializedName("content")
    val content: RenderedDto,

    @field:SerializedName("_embedded")
    val embedded: EmbeddedDto?
)

data class RenderedDto(
    @field:SerializedName("rendered")
    val rendered: String
)

data class EmbeddedDto(
    @field:SerializedName("wp:featuredmedia")
    val featuredMedia: List<FeaturedMediaDto>?
)

data class FeaturedMediaDto(
    @field:SerializedName("source_url")
    val sourceUrl: String
)
