package com.github.abrarshakhi.mytube.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.abrarshakhi.mytube.presentation.home.HomeScreen
import com.github.abrarshakhi.mytube.presentation.video.VideoScreen

@Composable
fun SetUpNavigation(
    navController: NavHostController, startDestination: Screen = Screen.Home
) {
    NavHost(
        navController = navController, startDestination = startDestination.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Videos.route, arguments = listOf(
                navArgument("channelId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val channelId = backStackEntry.arguments
                ?.getString("channelId")
                ?: return@composable

            VideoScreen(
                navController = navController,
                channelId = channelId
            )
        }
    }
}
