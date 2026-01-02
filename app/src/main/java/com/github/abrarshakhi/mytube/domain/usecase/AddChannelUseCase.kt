package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject


class AddChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(handle: String): Result<String> {
        return if (handle.isBlank()) {
            Result.failure(Exception("Blank Input"))
        } else if (handle.contains(' ')) {
            Result.failure(Exception("Should not contain spaces"))
        } else {
            repository.addChannel(handleAtChar(handle))
        }
    }

    private fun handleAtChar(handle: String): String {
        return if (handle.startsWith("@")) {
            handle.substring(1, handle.length)
        } else {
            handle
        }
    }
}