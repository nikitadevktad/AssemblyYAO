package com.nikitadevktad.assemblyyao.data.repository

import com.nikitadevktad.assemblyyao.data.local.NewsLocalDataSource
import com.nikitadevktad.assemblyyao.data.model.LoadResult
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.remote.NewsRemoteDataSource
import kotlinx.coroutines.CancellationException
import java.util.Locale

interface NewsRepository {
    suspend fun getNews(page: Int, searchQuery: String = ""): LoadResult<NewsPage>
    suspend fun getNews(id: Int): LoadResult<News>
}

class OfflineFirstNewsRepository(
    private val remote: NewsRemoteDataSource,
    private val local: NewsLocalDataSource,
) : NewsRepository {
    override suspend fun getNews(page: Int, searchQuery: String): LoadResult<NewsPage> {
        require(page > 0) { "Номер страницы должен быть положительным" }
        val remoteQuery = searchQuery.trim()
        // WordPress получает исходный регистр, а ключ кэша не зависит от регистра.
        val cacheKey = remoteQuery.lowercase(Locale.ROOT)
        val newsPage = try {
            remote.getNews(page, remoteQuery)
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            val cached = local.getPage(page, cacheKey)
            return cached?.let { LoadResult(it, isFromCache = true) }
                ?: throw NewsUnavailableException(
                    "Не удалось загрузить новости. Повторите попытку позже или проверьте подключение.",
                    exception,
                )
        }

        // Ошибка локального хранилища не должна скрывать уже полученные свежие данные.
        savePageToCache(page, cacheKey, newsPage)
        return LoadResult(newsPage, isFromCache = false)
    }

    override suspend fun getNews(id: Int): LoadResult<News> {
        val news = try {
            remote.getNews(id)
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            val cached = local.getNews(id)
            return cached?.let { LoadResult(it, isFromCache = true) }
                ?: throw NewsUnavailableException(
                    "Не удалось загрузить новость. Повторите попытку позже или проверьте подключение.",
                    exception,
                )
        }

        saveNewsToCache(news)
        return LoadResult(news, isFromCache = false)
    }

    private suspend fun savePageToCache(page: Int, searchQuery: String, newsPage: NewsPage) {
        try {
            local.savePage(page, searchQuery, newsPage)
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: Exception) {
            // Кэш является дополнением к свежему сетевому ответу.
        }
    }

    private suspend fun saveNewsToCache(news: News) {
        try {
            local.saveNews(news)
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: Exception) {
            // Кэш является дополнением к свежему сетевому ответу.
        }
    }
}

class NewsUnavailableException(
    message: String,
    cause: Throwable,
) : Exception(message, cause)
