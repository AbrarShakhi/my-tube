package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject


class FindChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(handle: String): Result<Channel> {
        return if (handle.isBlank() || handle == "@") {
            Result.failure(Exception("Blank input"))
        } else if (handle.contains(' ')) {
            Result.failure(Exception("Should not contain spaces"))
        } else if (handle.contains('\n') || handle.contains('\r')) {
            Result.failure(Exception("Invalid handle"))
        } else {
            repository.findChannel(handleAtChar(handle))
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