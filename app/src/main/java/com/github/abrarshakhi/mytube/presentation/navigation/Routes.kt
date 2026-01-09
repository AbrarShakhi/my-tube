package com.github.abrarshakhi.mytube.presentation.navigation

sealed class Routes {
    sealed class Root(val route: String) {
        object Home : Root("home")
        object IndividualVideo : Root("individual_video/{channelId}") {
            fun createRoute(channelId: String): String {
                return "individual_video/$channelId"
            }
        }
    }

    sealed class HomeBottom(val route: String) {
        object Channel : HomeBottom("channel")
        data object Video : HomeBottom("video")
    }

}