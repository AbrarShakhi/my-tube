package com.github.abrarshakhi.mytube.domain.usecase

import jakarta.inject.Inject


class ShowFilterOutcomeUseCase @Inject constructor() {
    operator fun invoke(regex: String, contains: Boolean): String {
        return when {
            regex.isEmpty() && contains -> "You will be notified for all videos."
            regex.isEmpty() && !contains -> "You won't get any notifications at all for this channel."
            contains -> "You will be notified for videos if their titles match this regex."
            else -> "You will be notified for videos if their titles do not match this regex."
        }
    }
}