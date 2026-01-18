package com.github.abrarshakhi.mytube.presentation.state

import com.github.abrarshakhi.mytube.domain.model.Video


sealed interface VideoListState {
    data object Loading : VideoListState
    data class Success(val videos: List<Video>) : VideoListState
    data class Error(val message: String) : VideoListState
}