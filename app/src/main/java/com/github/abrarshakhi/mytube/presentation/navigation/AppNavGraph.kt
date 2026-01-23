package com.github.abrarshakhi.mytube.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.abrarshakhi.mytube.presentation.MainViewModel
import com.github.abrarshakhi.mytube.presentation.screen.HomeScreen
import com.github.abrarshakhi.mytube.presentation.screen.IndividualVideoScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    NavHost(
        navController = navController, startDestination = Routes.Home.route
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(onNavigateToChannel = { channelId ->
                // navController.navigate(Routes.IndividualVideo.createRoute(channelId))
            }, viewModel = viewModel)
        }

        composable(
            route = Routes.IndividualVideo.route, arguments = listOf(
                navArgument("channelId") { type = NavType.StringType })
        ) { backStackEntry ->
            val channelId = backStackEntry.arguments
                ?.getString("channelId")
                ?: return@composable

            IndividualVideoScreen(
                channelId = channelId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onVideoClick = ExternalApp::openIn
            )
        }
    }
}
