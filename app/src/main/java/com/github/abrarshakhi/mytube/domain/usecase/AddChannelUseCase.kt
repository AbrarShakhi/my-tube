package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.usecase.result.Error
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import jakarta.inject.Inject


class AddChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(handle: String): Outcome<String> {
        return if (handle.isBlank()) {
            Outcome.Failure(Error.InvalidInputError("Blank Input"))
        } else if (handle.contains(' ')) {
            Outcome.Failure(Error.InvalidInputError("Should not contain spaces"))
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