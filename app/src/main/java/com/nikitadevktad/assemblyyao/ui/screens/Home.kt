package com.nikitadevktad.assemblyyao.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitadevktad.assemblyyao.R
import com.nikitadevktad.assemblyyao.ui.news.HomeNewsScreen

@Composable
fun HomeScreen(
    onClickMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_anr_cmyk),
                    contentDescription = stringResource(R.string.assembly_logo),
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .size(64.dp),
                )
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(R.string.name_main),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF85051B),
                    )
                    Text(
                        text = stringResource(R.string.bringing_cultures_together),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                IconButton(
                    onClick = onClickMenu,
                    modifier = Modifier,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.main_menu),
                        contentDescription = stringResource(R.string.menu),
                        tint = Color(0xFF85051B),
                    )
                }
            }
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_search),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.find_a_news_item)
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                singleLine = true,
            )
            Spacer(
                modifier = Modifier.padding(vertical = 5.dp)
            )
            HomeBanner(
                onClickAbout = {},
                modifier = Modifier,
            )
            HomeNewsScreen(
                clickNews = {

                },
                onAllNewsClick = {

                },
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen(
        onClickMenu = {},
        modifier = Modifier,
    )
}