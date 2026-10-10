package com.nikitadevktad.assemblyyao.ui.navigation

import androidx.annotation.DrawableRes
import com.nikitadevktad.assemblyyao.R

enum class TopLevelDestination(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int,
) {
    HOME("home", "Главная", R.drawable.home_buttom),
    NEWS("news", "Новости", R.drawable.news_buttom),
    ABOUT("about", "О нас", R.drawable.info),
    CONTACTS("contacts", "Контакты", R.drawable.contact_support)
}

object NewsDestination {
    val route = "news/{newsId}"
}