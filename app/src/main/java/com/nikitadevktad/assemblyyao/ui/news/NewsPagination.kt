package com.nikitadevktad.assemblyyao.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NewsPagination(
    currentPage: Int,
    totalPages: Int,
    onPageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = paginationItems(currentPage, totalPages)
    if (items.isEmpty()) return

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            when (item) {
                PaginationItem.Ellipsis -> Text(
                    text = "…",
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                is PaginationItem.Page -> {
                    val page = item.number
                    Text(
                        text = page.toString(),
                        fontWeight = if (page == currentPage) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable(enabled = page != currentPage) { onPageClick(page) },
                    )
                }
            }
        }
    }
}

sealed interface PaginationItem {
    data class Page(val number: Int) : PaginationItem
    data object Ellipsis : PaginationItem
}

fun paginationItems(currentPage: Int, totalPages: Int): List<PaginationItem> {
    if (totalPages <= 1) return emptyList()
    val pages = when {
        totalPages <= 7 -> (1..totalPages).map { PaginationItem.Page(it) }
        currentPage <= 4 -> (1..5).map { PaginationItem.Page(it) } +
            PaginationItem.Ellipsis + PaginationItem.Page(totalPages)
        currentPage >= totalPages - 3 -> listOf(
            PaginationItem.Page(1),
            PaginationItem.Ellipsis,
        ) + ((totalPages - 4)..totalPages).map { PaginationItem.Page(it) }
        else -> listOf(
            PaginationItem.Page(1),
            PaginationItem.Ellipsis,
            PaginationItem.Page(currentPage - 1),
            PaginationItem.Page(currentPage),
            PaginationItem.Page(currentPage + 1),
            PaginationItem.Ellipsis,
            PaginationItem.Page(totalPages),
        )
    }
    return pages
}
