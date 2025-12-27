package com.github.abrarshakhi.mytube.presentation.home

import com.github.abrarshakhi.mytube.domain.model.Channel


data class HomeUiState(
    val channels: List<Channel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

