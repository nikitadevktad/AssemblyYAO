package com.nikitadevktad.assemblyyao.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nikitadevktad.assemblyyao.data.local.entity.NewsEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageEntity
import com.nikitadevktad.assemblyyao.data.local.entity.NewsPageItemEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_pages WHERE searchQuery = :searchQuery AND page = :page LIMIT 1")
    suspend fun getPage(searchQuery: String, page: Int): NewsPageEntity?

    @Query(
        """
        SELECT news.* FROM news
        INNER JOIN news_page_items ON news.id = news_page_items.newsId
        WHERE news_page_items.searchQuery = :searchQuery AND news_page_items.page = :page
        ORDER BY news_page_items.position
        """
    )
    suspend fun getPageNews(searchQuery: String, page: Int): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getNews(id: Int): NewsEntity?

    @Upsert
    suspend fun upsertNews(news: List<NewsEntity>)

    @Upsert
    suspend fun upsertNews(news: NewsEntity)

    @Upsert
    suspend fun upsertPage(page: NewsPageEntity)

    @Upsert
    suspend fun upsertPageItems(items: List<NewsPageItemEntity>)

    @Query("DELETE FROM news_page_items WHERE searchQuery = :searchQuery AND page = :page")
    suspend fun deletePageItems(searchQuery: String, page: Int)

    @Transaction
    suspend fun replacePage(
        page: NewsPageEntity,
        items: List<NewsPageItemEntity>,
        news: List<NewsEntity>,
    ) {
        // Метаданные, новости и их порядок должны обновиться одной транзакцией.
        upsertNews(news)
        upsertPage(page)
        deletePageItems(page.searchQuery, page.page)
        upsertPageItems(items)
    }
}
