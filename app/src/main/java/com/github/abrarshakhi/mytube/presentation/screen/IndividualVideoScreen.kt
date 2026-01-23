package com.github.abrarshakhi.mytube.presentation.screen

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.github.abrarshakhi.mytube.domain.model.Video
import com.github.abrarshakhi.mytube.presentation.MainViewModel

@Composable
fun IndividualVideoScreen(
    channelId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onVideoClick: (context: Context, video: Video) -> Unit
) {

}