package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject

class AddChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channel: Channel): Result<String> {
        return if (channel.filter.regex.isEmpty() && !channel.filter.contains) {
            Result.failure(
                IllegalArgumentException(
                    "You won't get any notifications at all for this channel."
                )
            )
        } else if (channel.filter.regex.contains('\n') || channel.filter.regex.contains('\r')) {
            Result.failure(
                IllegalArgumentException(
                    "Regex string should not contain new line"
                )
            )

        } else {
            repository.addChannel(channel)
        }
    }
}