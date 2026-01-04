package com.github.abrarshakhi.mytube.domain.usecase


class ShowFilterOutcomeUseCase {
    operator fun invoke(regex: String, contains: Boolean): String {
        return if (contains) {
            regex
        } else {
            "!$regex"
        }
    }
}