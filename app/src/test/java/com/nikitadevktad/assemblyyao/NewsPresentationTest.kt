package com.nikitadevktad.assemblyyao

import com.nikitadevktad.assemblyyao.data.model.LoadResult
import com.nikitadevktad.assemblyyao.data.model.News
import com.nikitadevktad.assemblyyao.data.model.NewsPage
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository
import com.nikitadevktad.assemblyyao.ui.navigation.NewsDestination
import com.nikitadevktad.assemblyyao.ui.news.NewsViewModel
import com.nikitadevktad.assemblyyao.ui.news.PaginationItem
import com.nikitadevktad.assemblyyao.ui.news.formatDate
import com.nikitadevktad.assemblyyao.ui.news.paginationItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsPresentationTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun viewModelLoadsCachedStateAndExposesOfflineFlag() = runTest(mainDispatcherRule.dispatcher) {
        val repository = FakeRepository(
            pageResult = LoadResult(pageOf(news(3)), isFromCache = true),
        )

        val viewModel = NewsViewModel(repository)
        advanceUntilIdle()

        assertEquals(listOf(3), viewModel.ui.news.map { it.id })
        assertTrue(viewModel.ui.isFromCache)
        assertFalse(viewModel.ui.isLoading)
    }

    @Test
    fun searchIsDebouncedAndResetsPage() = runTest(mainDispatcherRule.dispatcher) {
        val repository = FakeRepository()
        val viewModel = NewsViewModel(repository)
        advanceUntilIdle()

        viewModel.updateSearchQuery("музыка")
        advanceTimeBy(349)
        assertEquals(listOf(""), repository.queries)
        advanceTimeBy(1)
        advanceUntilIdle()

        assertEquals(listOf("", "музыка"), repository.queries)
        assertEquals(1, viewModel.ui.currentPage)
    }

    @Test
    fun paginationBuildsMiddleWindow() {
        assertEquals(
            listOf(
                PaginationItem.Page(1),
                PaginationItem.Ellipsis,
                PaginationItem.Page(4),
                PaginationItem.Page(5),
                PaginationItem.Page(6),
                PaginationItem.Ellipsis,
                PaginationItem.Page(10),
            ),
            paginationItems(currentPage = 5, totalPages = 10),
        )
    }

    @Test
    fun dateFormattingSupportsLocalAndOffsetDatesAndKeepsUnknownValue() {
        assertEquals("15 октября 2026", formatDate("2026-10-15T10:30:00"))
        assertEquals("15 октября 2026", formatDate("2026-10-15T10:30:00+03:00"))
        assertEquals("неизвестно", formatDate("неизвестно"))
    }

    @Test
    fun newsDetailsRouteIsBuiltFromId() {
        assertEquals("news/42", NewsDestination.createRoute(42))
    }
}

private class FakeRepository(
    var pageResult: LoadResult<NewsPage> = LoadResult(pageOf(news(1)), false),
) : NewsRepository {
    val queries = mutableListOf<String>()

    override suspend fun getNews(page: Int, searchQuery: String): LoadResult<NewsPage> {
        queries += searchQuery
        return pageResult
    }

    override suspend fun getNews(id: Int): LoadResult<News> = LoadResult(news(id), false)
}
