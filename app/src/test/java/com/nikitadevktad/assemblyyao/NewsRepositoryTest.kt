package com.nikitadevktad.assemblyyao

import com.nikitadevktad.assemblyyao.data.local.NewsLocalDataSource
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.remote.NewsRemoteDataSource
import com.nikitadevktad.assemblyyao.data.repository.NewsUnavailableException
import com.nikitadevktad.assemblyyao.data.repository.OfflineFirstNewsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class NewsRepositoryTest {
    @Test
    fun successfulNetworkPageIsCached() = runTest {
        val page = pageOf(news(1))
        val remote = FakeRemote(pageResult = Result.success(page))
        val local = FakeLocal()
        val repository = OfflineFirstNewsRepository(remote, local)

        val result = repository.getNews(page = 1, searchQuery = " Культура ")

        assertFalse(result.isFromCache)
        assertSame(page, result.data)
        assertEquals("культура", local.savedSearchQuery)
        assertSame(page, local.savedPage)
    }

    @Test
    fun cacheWriteFailureDoesNotHideFreshPage() = runTest {
        val page = pageOf(news(4))
        val local = FakeLocal(savePageFailure = IllegalStateException("disk full"))
        val repository = OfflineFirstNewsRepository(
            remote = FakeRemote(pageResult = Result.success(page)),
            local = local,
        )

        val result = repository.getNews(page = 1)

        assertFalse(result.isFromCache)
        assertSame(page, result.data)
    }

    @Test
    fun networkFailureReturnsCachedPage() = runTest {
        val cached = pageOf(news(2))
        val remote = FakeRemote(pageResult = Result.failure(IllegalStateException("offline")))
        val local = FakeLocal(cachedPage = cached)
        val repository = OfflineFirstNewsRepository(remote, local)

        val result = repository.getNews(page = 1)

        assertTrue(result.isFromCache)
        assertSame(cached, result.data)
    }

    @Test(expected = NewsUnavailableException::class)
    fun networkFailureWithoutCacheReturnsFriendlyError() = runTest {
        val remote = FakeRemote(pageResult = Result.failure(IllegalStateException("offline")))
        OfflineFirstNewsRepository(remote, FakeLocal()).getNews(page = 1)
    }

    @Test
    fun detailsFallBackToCachedNews() = runTest {
        val cached = news(7)
        val remote = FakeRemote(newsResult = Result.failure(IllegalStateException("offline")))
        val repository = OfflineFirstNewsRepository(remote, FakeLocal(cachedNews = cached))

        val result = repository.getNews(7)

        assertTrue(result.isFromCache)
        assertSame(cached, result.data)
    }

    @Test
    fun cacheWriteFailureDoesNotHideFreshDetails() = runTest {
        val fresh = news(8)
        val local = FakeLocal(saveNewsFailure = IllegalStateException("disk full"))
        val repository = OfflineFirstNewsRepository(
            remote = FakeRemote(newsResult = Result.success(fresh)),
            local = local,
        )

        val result = repository.getNews(8)

        assertFalse(result.isFromCache)
        assertSame(fresh, result.data)
    }
}

private class FakeRemote(
    var pageResult: Result<NewsPage> = Result.success(pageOf(news(1))),
    var newsResult: Result<News> = Result.success(news(1)),
) : NewsRemoteDataSource {
    override suspend fun getNews(page: Int, searchQuery: String) = pageResult.getOrThrow()
    override suspend fun getNews(id: Int) = newsResult.getOrThrow()
}

private class FakeLocal(
    var cachedPage: NewsPage? = null,
    var cachedNews: News? = null,
    private val savePageFailure: Exception? = null,
    private val saveNewsFailure: Exception? = null,
) : NewsLocalDataSource {
    var savedPage: NewsPage? = null
    var savedSearchQuery: String? = null

    override suspend fun getPage(page: Int, searchQuery: String) = cachedPage
    override suspend fun getNews(id: Int) = cachedNews

    override suspend fun savePage(page: Int, searchQuery: String, newsPage: NewsPage) {
        savePageFailure?.let { throw it }
        savedPage = newsPage
        savedSearchQuery = searchQuery
    }

    override suspend fun saveNews(news: News) {
        saveNewsFailure?.let { throw it }
        cachedNews = news
    }
}

internal fun news(id: Int) = News(
    id = id,
    date = "2026-10-15T10:30:00",
    title = "Новость $id",
    excerpt = "Анонс",
    content = "Текст",
    imageUrl = null,
    link = "https://example.test/$id",
)

internal fun pageOf(vararg items: News) = NewsPage(
    news = items.toList(),
    totalPages = 3,
    totalNews = items.size,
)
