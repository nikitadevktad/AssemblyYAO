package com.nikitadevktad.assemblyyao

import com.google.gson.Gson
import com.nikitadevktad.assemblyyao.data.mapper.toNews
import com.nikitadevktad.assemblyyao.data.remote.dto.EmbeddedDto
import com.nikitadevktad.assemblyyao.data.remote.dto.FeaturedMediaDto
import com.nikitadevktad.assemblyyao.data.remote.dto.NewsDto
import com.nikitadevktad.assemblyyao.data.remote.dto.RenderedDto
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsMapperTest {
    @Test
    fun mapsWordPressDtoToDomainModel() {
        val result = NewsDto(
            id = 42,
            date = "2026-10-15T10:30:00",
            link = "https://example.test/news/42",
            title = RenderedDto("Заголовок"),
            excerpt = RenderedDto("Анонс"),
            content = RenderedDto("Текст"),
            embedded = EmbeddedDto(listOf(FeaturedMediaDto("https://example.test/image.jpg"))),
        ).toNews()

        assertEquals(42, result.id)
        assertEquals("Заголовок", result.title)
        assertEquals("Текст", result.content)
        assertEquals("https://example.test/image.jpg", result.imageUrl)
    }

    @Test
    fun deserializesWordPressJsonUsingStableFieldNames() {
        val json = """
            {
              "id": 42,
              "date": "2026-10-15T10:30:00",
              "link": "https://example.test/news/42",
              "title": {"rendered": "Заголовок"},
              "excerpt": {"rendered": "Анонс"},
              "content": {"rendered": "Текст"},
              "_embedded": {
                "wp:featuredmedia": [
                  {"source_url": "https://example.test/image.jpg"}
                ]
              }
            }
        """.trimIndent()

        val result = Gson().fromJson(json, NewsDto::class.java).toNews()

        assertEquals(42, result.id)
        assertEquals("Заголовок", result.title)
        assertEquals("Текст", result.content)
        assertEquals("https://example.test/image.jpg", result.imageUrl)
    }
}
