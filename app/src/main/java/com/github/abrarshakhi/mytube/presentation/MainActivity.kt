package com.github.abrarshakhi.mytube.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.github.abrarshakhi.mytube.data.local.AppDatabase
import com.github.abrarshakhi.mytube.data.repository.ChannelRepositoryImpl
import com.github.abrarshakhi.mytube.domain.usecase.AddChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.GetChannelsUseCase
import com.github.abrarshakhi.mytube.presentation.home.HomeScreen
import com.github.abrarshakhi.mytube.presentation.home.HomeViewModel
import com.github.abrarshakhi.mytube.ui.theme.MyTubeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTubeTheme {
                HomeScreen()
            }
        }
    }
}