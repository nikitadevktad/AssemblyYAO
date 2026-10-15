package com.nikitadevktad.assemblyyao

import com.nikitadevktad.assemblyyao.data.local.RoomNewsLocalDataSource
import com.nikitadevktad.assemblyyao.data.local.dao.NewsDao
import com.nikitadevktad.assemblyyao.data.local.entity.NewsEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageItemEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NewsLocalDataSourceTest {
    @Test
    fun expiredPageIsNotReturnedOrRead() = runTest {
        val dao = FakeNewsDao(
            page = pageEntity(cachedAt = 8_999L),
            pageNews = listOf(newsEntity(cachedAt = 8_999L)),
        )
        val source = RoomNewsLocalDataSource(
            dao = dao,
            now = { 10_000L },
            maxCacheAgeMillis = 1_000L,
        )

        assertNull(source.getPage(page = 1, searchQuery = ""))
        assertEquals(0, dao.pageNewsReads)
    }

    @Test
    fun cacheAtAgeLimitIsStillAvailable() = runTest {
        val dao = FakeNewsDao(
            page = pageEntity(cachedAt = 9_000L),
            pageNews = listOf(newsEntity(cachedAt = 9_000L)),
            news = newsEntity(cachedAt = 9_000L),
        )
        val source = RoomNewsLocalDataSource(
            dao = dao,
            now = { 10_000L },
            maxCacheAgeMillis = 1_000L,
        )

        assertEquals(listOf(1), source.getPage(page = 1, searchQuery = "")?.news?.map { it.id })
        assertEquals(1, source.getNews(1)?.id)
    }

    @Test
    fun expiredDetailsAreNotReturned() = runTest {
        val source = RoomNewsLocalDataSource(
            dao = FakeNewsDao(news = newsEntity(cachedAt = 8_999L)),
            now = { 10_000L },
            maxCacheAgeMillis = 1_000L,
        )

        assertNull(source.getNews(1))
    }
}

private class FakeNewsDao(
    private var page: NewsPageEntity? = null,
    private var pageNews: List<NewsEntity> = emptyList(),
    private var news: NewsEntity? = null,
) : NewsDao {
    var pageNewsReads: Int = 0

    override suspend fun getPage(searchQuery: String, page: Int): NewsPageEntity? = this.page

    override suspend fun getPageNews(searchQuery: String, page: Int): List<NewsEntity> {
        pageNewsReads += 1
        return pageNews
    }

    override suspend fun getNews(id: Int): NewsEntity? = news

    override suspend fun upsertNews(news: List<NewsEntity>) {
        pageNews = news
    }

    override suspend fun upsertNews(news: NewsEntity) {
        this.news = news
    }

    override suspend fun upsertPage(page: NewsPageEntity) {
        this.page = page
    }

    override suspend fun upsertPageItems(items: List<NewsPageItemEntity>) = Unit

    override suspend fun deletePageItems(searchQuery: String, page: Int) = Unit
}

private fun pageEntity(cachedAt: Long) = NewsPageEntity(
    searchQuery = "",
    page = 1,
    totalPages = 3,
    totalNews = 1,
    cachedAt = cachedAt,
)

private fun newsEntity(cachedAt: Long) = NewsEntity(
    id = 1,
    date = "2026-10-15T10:30:00",
    title = "Новость",
    excerpt = "Анонс",
    content = "Текст",
    imageUrl = null,
    link = "https://example.test/1",
    cachedAt = cachedAt,
)
