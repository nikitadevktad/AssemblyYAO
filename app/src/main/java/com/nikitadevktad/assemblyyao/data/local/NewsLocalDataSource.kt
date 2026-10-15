package com.nikitadevktad.assemblyyao.data.local

import com.nikitadevktad.assemblyyao.data.local.dao.NewsDao
import com.nikitadevktad.assemblyyao.data.local.entity.NewsEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageItemEntity
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage

interface NewsLocalDataSource {
    suspend fun getPage(page: Int, searchQuery: String): NewsPage?
    suspend fun getNews(id: Int): News?
    suspend fun savePage(page: Int, searchQuery: String, newsPage: NewsPage)
    suspend fun saveNews(news: News)
}

class RoomNewsLocalDataSource(
    private val dao: NewsDao,
    private val now: () -> Long = System::currentTimeMillis,
    private val maxCacheAgeMillis: Long = DEFAULT_CACHE_MAX_AGE_MILLIS,
) : NewsLocalDataSource {
    init {
        require(maxCacheAgeMillis >= 0) { "Срок хранения кэша не может быть отрицательным" }
    }

    override suspend fun getPage(page: Int, searchQuery: String): NewsPage? {
        val metadata = dao.getPage(searchQuery, page) ?: return null
        if (!isFresh(metadata.cachedAt)) return null
        return NewsPage(
            news = dao.getPageNews(searchQuery, page).map(NewsEntity::toModel),
            totalPages = metadata.totalPages,
            totalNews = metadata.totalNews,
        )
    }

    override suspend fun getNews(id: Int): News? {
        val news = dao.getNews(id) ?: return null
        return news.takeIf { isFresh(it.cachedAt) }?.toModel()
    }

    override suspend fun savePage(page: Int, searchQuery: String, newsPage: NewsPage) {
        val cachedAt = now()
        dao.replacePage(
            page = NewsPageEntity(
                searchQuery = searchQuery,
                page = page,
                totalPages = newsPage.totalPages,
                totalNews = newsPage.totalNews,
                cachedAt = cachedAt,
            ),
            items = newsPage.news.mapIndexed { index, news ->
                NewsPageItemEntity(
                    searchQuery = searchQuery,
                    page = page,
                    newsId = news.id,
                    position = index,
                )
            },
            news = newsPage.news.map { it.toEntity(cachedAt) },
        )
    }

    override suspend fun saveNews(news: News) {
        dao.upsertNews(news.toEntity(now()))
    }

    // Данные старше семи суток не используются как офлайн-ответ.
    private fun isFresh(cachedAt: Long): Boolean =
        cachedAt >= now() - maxCacheAgeMillis

    private companion object {
        const val DEFAULT_CACHE_MAX_AGE_MILLIS = 7L * 24 * 60 * 60 * 1_000
    }
}

private fun NewsEntity.toModel() = News(
    id = id,
    date = date,
    title = title,
    excerpt = excerpt,
    content = content,
    imageUrl = imageUrl,
    link = link,
)

private fun News.toEntity(cachedAt: Long) = NewsEntity(
    id = id,
    date = date,
    title = title,
    excerpt = excerpt,
    content = content,
    imageUrl = imageUrl,
    link = link,
    cachedAt = cachedAt,
)
