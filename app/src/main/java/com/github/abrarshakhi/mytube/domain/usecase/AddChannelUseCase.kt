package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import jakarta.inject.Inject


class AddChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channelId: String): Outcome<String> {
        return when (
            val channel = repository.findChannel(channelId)
        ) {
            is Outcome.Failure -> Outcome.Failure(channel.error)
            is Outcome.Success -> return repository.addChannel(channel.value)
        }
    }
}