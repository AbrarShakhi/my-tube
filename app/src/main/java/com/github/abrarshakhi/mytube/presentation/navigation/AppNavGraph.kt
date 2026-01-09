package com.github.abrarshakhi.mytube.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.abrarshakhi.mytube.presentation.screen.HomeScreen
import com.github.abrarshakhi.mytube.presentation.screen.IndividualVideoScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.Root.Home.route
    ) {
        composable(route = Routes.Root.Home.route) {
            HomeScreen(onNavigateToChannel = { channelId ->
                navController.navigate(Routes.Root.IndividualVideo.createRoute(channelId))
            })
        }

        composable(
            route = Routes.Root.IndividualVideo.route, arguments = listOf(
                navArgument("channelId") { type = NavType.StringType })
        ) { backStackEntry ->
            val channelId = backStackEntry.arguments
                ?.getString("channelId")
                ?: return@composable

            IndividualVideoScreen(channelId = channelId, onBack = {
                navController.popBackStack()
            })
        }
    }
}
