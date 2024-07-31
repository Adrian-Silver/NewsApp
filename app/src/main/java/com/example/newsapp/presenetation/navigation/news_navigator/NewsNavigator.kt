package com.example.newsapp.presenetation.navigation.news_navigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.data.local.ArticleEntity
import com.example.newsapp.presenetation.bookmark.BookmarkScreen
import com.example.newsapp.presenetation.bookmark.BookmarkViewModel
import com.example.newsapp.presenetation.details.DetailsScreen
import com.example.newsapp.presenetation.details.DetailsViewModel
import com.example.newsapp.presenetation.home.HomeScreen
import com.example.newsapp.presenetation.home.HomeViewModel
import com.example.newsapp.presenetation.navigation.Route
import com.example.newsapp.presenetation.navigation.news_navigator.components.BottomNavigationItem
import com.example.newsapp.presenetation.navigation.news_navigator.components.NewsBottomNavigation
import com.example.newsapp.presenetation.search.SearchScreen
import com.example.newsapp.presenetation.search.SearchViewModel

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_handball, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    // Hide bottomNavbar when in details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }

                    }
                )
            }
        }
    ) {

        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()

                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            articleEntity = article
                        )
                    }
                )
            }

            composable(route = Route.SearchScreen.route) {

                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value

                OnBackClickStateSaver(navController = navController)

                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            articleEntity = article
                        )
                    }
                )
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value

                OnBackClickStateSaver(navController = navController)

                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            articleEntity = article
                        )
                    }
                )
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<ArticleEntity>("article")
                    ?.let { article ->
                        DetailsScreen(
                            articleEntity = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = viewModel.sideEffect
                        )
                    }
            }

        }

    }

}

// Navigate to the Home Screen instead of exiting the app when the back button is pressed
@Composable
fun OnBackClickStateSaver(navController: NavController) {
    // BackHandler - intercepts the back button press and executes the provided lambda function
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            // popUpTo - pops up to the start destination of the nav graph and saves the state
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        // Ensure only one instance of the destination is in the backstack and restores the previous state
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, articleEntity: ArticleEntity) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", articleEntity)
    navController.navigate(
        route = Route.DetailScreen.route
    )
}