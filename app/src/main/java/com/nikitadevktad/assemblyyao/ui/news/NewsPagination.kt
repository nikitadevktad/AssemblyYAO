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
    if (totalPages <= 1) return

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (totalPages <= 7) {

            for (page in 1..totalPages) {

                Text(
                    text = page.toString(), fontWeight = if (page == currentPage) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },

                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onPageClick(page)
                        })
            }
        } else if (currentPage <= 4) {

            for (page in 1..5) {

                Text(
                    text = page.toString(), fontWeight = if (page == currentPage) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }, modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onPageClick(page)
                        })
            }
            Text(
                text = "...", modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = totalPages.toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onPageClick(totalPages)
                    })
        } else if (currentPage >= totalPages - 3) {
            Text(
                text = "1", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onPageClick(1)
                    })
            Text(
                text = "...", modifier = Modifier.padding(horizontal = 8.dp)
            )
            for (page in (totalPages - 4)..totalPages) {
                Text(
                    text = page.toString(), fontWeight = if (page == currentPage) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }, modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onPageClick(page)
                        })
            }
        } else {
            Text(
                text = "1", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onPageClick(1)
                    })

            Text(
                text = "...", modifier = Modifier.padding(horizontal = 8.dp)
            )
            for (page in (currentPage - 1)..(currentPage + 1)) {

                Text(
                    text = page.toString(), fontWeight = if (page == currentPage) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }, modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onPageClick(page)
                        })
            }

            Text(
                text = "...", modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = totalPages.toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onPageClick(totalPages)
                    })
        }
    }
}