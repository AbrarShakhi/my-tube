package com.github.abrarshakhi.mytube.presentation.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Videos : Screen("videos/{channelId}") {
        fun createRoute(channelId: String): String {
            return "videos/$channelId"
        }
    }
}
