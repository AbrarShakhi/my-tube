package com.github.abrarshakhi.mytube.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.abrarshakhi.mytube.presentation.MainViewModel
import com.github.abrarshakhi.mytube.presentation.components.BottomBar
import com.github.abrarshakhi.mytube.presentation.navigation.Routes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    onNavigateToChannel: (String) -> Unit, viewModel: MainViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController = bottomNavController)
    }, topBar = {
        TopAppBar(title = { Text("MyTube") })
    }, floatingActionButton = {
        val currentRoute =
            bottomNavController.currentBackStackEntryAsState().value?.destination?.route

        if (currentRoute == Routes.HomeBottom.Channel.route) {
            FloatingActionButton(onClick = { viewModel.showSheet() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Channel")
            }
        } else if (currentRoute == Routes.HomeBottom.Video.route) {
            FloatingActionButton(onClick = { viewModel.syncVideos() }) {
                Icon(Icons.Default.Sync, contentDescription = "Sync")
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HomeBottom.Video.route,
        ) {
            composable(Routes.HomeBottom.Video.route) {
                VideoScreen(paddingValues)
            }

            composable(Routes.HomeBottom.Channel.route) {
                ChannelScreen(
                    onNavigateToChannel = onNavigateToChannel, paddingValues = paddingValues
                )
            }

        }
    }
}