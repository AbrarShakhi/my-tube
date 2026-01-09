package com.github.abrarshakhi.mytube.presentation.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home") {
        object Channel : Routes("channel")
        object Video : Routes("video")
    }

    object IndividualVideo : Routes("individual_video/{channelId}") {
        fun createRoute(channelId: String): String {
            return "individual_video/$channelId"
        }
    }
}
