package com.github.abrarshakhi.mytube.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.presentation.MainViewModel
import com.github.abrarshakhi.mytube.presentation.components.ErrorContent
import com.github.abrarshakhi.mytube.presentation.components.LoadingContent
import com.github.abrarshakhi.mytube.presentation.components.VideoItem
import com.github.abrarshakhi.mytube.presentation.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val videoListState by viewModel.videoListState.collectAsState()

    if (videoListState.isLoading) {
        LoadingContent(modifier = Modifier.padding(paddingValues))
    }

    when (val state = videoListState.content) {
        is UiState.UiContent.Data ->
            LazyColumn(Modifier.padding(paddingValues)) {
                items(state.value) { video ->
                    VideoItem(video = video, modifier = Modifier.padding(8.dp), onClick = {
                        // TODO: open youtube...
                    })
                }
            }

        is UiState.UiContent.Error -> ErrorContent(
            text = state.message,
            modifier = Modifier.padding(paddingValues)
        )
    }
}