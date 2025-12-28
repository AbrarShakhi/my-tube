package com.github.abrarshakhi.mytube.domain.model

data class Channel(
    val channelId: String,
    val name: String,
    val hasNewUpload: Boolean,
    val lastUploadedSince: String
)