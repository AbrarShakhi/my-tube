package com.github.abrarshakhi.mytube.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import com.github.abrarshakhi.mytube.presentation.navigation.AppNavGraph
import com.github.abrarshakhi.mytube.presentation.screen.VideoScreen
import com.github.abrarshakhi.mytube.presentation.worker.SyncScheduler
import com.github.abrarshakhi.mytube.ui.theme.MyTubeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyTubeTheme { AppNavGraph() } }
        SyncScheduler.schedule(this)
    }
}