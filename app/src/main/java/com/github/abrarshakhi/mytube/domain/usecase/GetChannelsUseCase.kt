package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import jakarta.inject.Inject


class GetChannelsUseCase @Inject constructor(
    private val repository: ChannelRepository
) {
    suspend operator fun invoke(): Outcome<List<Channel>> {
        return repository.getChannels()
    }
}