package com.nikitadevktad.assemblyyao.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository
import com.nikitadevktad.assemblyyao.ui.about.AboutScreen
import com.nikitadevktad.assemblyyao.ui.contacts.ContactsScreen
import com.nikitadevktad.assemblyyao.ui.home.HomeScreen
import com.nikitadevktad.assemblyyao.ui.news.NewsScreen
import com.nikitadevktad.assemblyyao.ui.news.details.NewsDetailsScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    repository: NewsRepository,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val selectedDestination = TopLevelDestination.entries.firstOrNull { destination ->
        currentDestination?.hierarchy?.any { it.route == destination.route } == true
    }
    val navigateTopLevel: (TopLevelDestination) -> Unit = { destination ->
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(com.nikitadevktad.assemblyyao.R.string.app_navigation_menu),
                    modifier = Modifier.padding(20.dp),
                )
                TopLevelDestination.entries.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(stringResource(destination.titleRes)) },
                        selected = destination == selectedDestination,
                        onClick = {
                            navigateTopLevel(destination)
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.iconRes),
                                contentDescription = null,
                            )
                        },
                        modifier = Modifier.testTag("drawer_${destination.route}"),
                    )
                }
            }
        },
    ) {
        Scaffold(
            bottomBar = {
                selectedDestination?.let { destination ->
                    AppBottomBar(
                        selectedDestination = destination,
                        onClickIcon = navigateTopLevel,
                    )
                }
            },
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.HOME.route,
                modifier = Modifier.padding(padding),
            ) {
                composable(TopLevelDestination.HOME.route) {
                    HomeScreen(
                        repository = repository,
                        onClickMenu = { scope.launch { drawerState.open() } },
                        onNewsClick = { newsId ->
                            navController.navigate(NewsDestination.createRoute(newsId))
                        },
                        onAllNewsClick = { navigateTopLevel(TopLevelDestination.NEWS) },
                        onClickAbout = { navigateTopLevel(TopLevelDestination.ABOUT) },
                    )
                }
                composable(
                    route = NewsDestination.route,
                    arguments = listOf(navArgument("newsId") { type = NavType.IntType }),
                ) { entry ->
                    entry.arguments?.getInt("newsId")?.let { newsId ->
                        NewsDetailsScreen(newsId = newsId, repository = repository)
                    }
                }
                composable(TopLevelDestination.NEWS.route) {
                    NewsScreen(
                        repository = repository,
                        onNewsClick = { newsId ->
                            navController.navigate(NewsDestination.createRoute(newsId))
                        },
                    )
                }
                composable(TopLevelDestination.ABOUT.route) {
                    AboutScreen()
                }
                composable(TopLevelDestination.CONTACTS.route) {
                    ContactsScreen()
                }
            }
        }
    }
}
