package com.example.inshortsassignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.inshortsassignment.ui.screen.bookmarks.BookmarksScreen
import com.example.inshortsassignment.ui.screen.details.DetailsScreen
import com.example.inshortsassignment.ui.screen.home.HomeScreen
import com.example.inshortsassignment.ui.screen.search.SearchScreen

object Routes {
    const val HOME = "home"
    const val DETAILS = "details"
    const val SEARCH = "search"
    const val BOOKMARKS = "bookmarks"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.SEARCH) {
            SearchScreen(navController)
        }
        composable(Routes.BOOKMARKS) {
            BookmarksScreen(navController)
        }
        composable(
            route = "${Routes.DETAILS}/{movieId}",
            deepLinks = listOf(navDeepLink {
                uriPattern = "https://inshorts-tsk.web.app/movie/{movieId}"
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: return@composable
            DetailsScreen(movieId = movieId, navController = navController)
        }


    }
}
