package com.nikitadevktad.assemblyyao.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nikitadevktad.assemblyyao.R

enum class TopLevelDestination(
    val route: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
) {
    HOME("home", R.string.home, R.drawable.home_buttom),
    NEWS("news", R.string.news, R.drawable.news_buttom),
    ABOUT("about", R.string.about_us, R.drawable.info),
    CONTACTS("contacts", R.string.contacts, R.drawable.contact_support),
}

object NewsDestination {
    const val route = "news/{newsId}"
    fun createRoute(newsId: Int) = "news/$newsId"
}
