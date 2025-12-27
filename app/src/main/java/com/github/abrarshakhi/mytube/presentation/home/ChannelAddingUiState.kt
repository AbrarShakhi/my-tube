package com.github.abrarshakhi.mytube.presentation.home


data class ChannelAddingUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)