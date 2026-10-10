package com.nikitadevktad.assemblyyao.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikitadevktad.assemblyyao.ui.news.details.NewsDetailsScreen
import com.nikitadevktad.assemblyyao.ui.screens.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectionDestination = TopLevelDestination.entries.firstOrNull() {
        it.route == currentRoute
    } ?: TopLevelDestination.HOME

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selectedDestination = selectionDestination,
                onClickIcon = { destination ->
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                    }
                },
            )
        }

    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.HOME.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(TopLevelDestination.HOME.route) {
                HomeScreen(
                    onClickMenu = {},
                    onNewsClick = { newsId ->
                        navController.navigate("news/$newsId")

                    }
                )
            }
            composable(
                route = NewsDestination.route,
                arguments = listOf(
                    navArgument("newsId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val newsId = backStackEntry.arguments?.getInt("newsId")
                if (newsId != null) {
                    NewsDetailsScreen(
                        newsId = newsId
                    )
                }
            }
        }
    }
}