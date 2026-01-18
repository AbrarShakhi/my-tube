package com.github.abrarshakhi.mytube.domain.usecase

import com.github.abrarshakhi.mytube.domain.repository.SyncRepository
import javax.inject.Inject

class SyncVideosUseCase @Inject constructor(
    private val repository: SyncRepository
) {
    suspend operator fun invoke():Result<Unit>{
        return repository.syncVideos()
    }
}