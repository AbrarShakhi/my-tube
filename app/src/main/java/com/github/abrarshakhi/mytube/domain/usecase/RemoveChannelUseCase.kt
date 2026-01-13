package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject

class RemoveChannelUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(channel: Channel): Result<Unit> {
        return repository.removeChannel(channel)
    }
}