package com.github.abrarshakhi.mytube.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.github.abrarshakhi.mytube.presentation.navigation.SetUpNavigation
import com.github.abrarshakhi.mytube.ui.theme.MyTubeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTubeTheme {
                val navController = rememberNavController()
                SetUpNavigation(
                    navController = navController,
                )
            }
        }
    }
}