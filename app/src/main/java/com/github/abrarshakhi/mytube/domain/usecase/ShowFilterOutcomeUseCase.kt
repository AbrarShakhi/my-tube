package com.github.abrarshakhi.mytube.domain.usecase

import jakarta.inject.Inject


class ShowFilterOutcomeUseCase @Inject constructor() {
    operator fun invoke(regex: String, contains: Boolean): String {
        return if (contains) {
            regex
        } else {
            "!$regex"
        }
    }
}