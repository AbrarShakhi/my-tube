package com.github.abrarshakhi.mytube.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.mytube.domain.usecase.AddChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.GetChannelsUseCase
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChannelsUseCase: GetChannelsUseCase,
    private val addChannelUseCase: AddChannelUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _channelAddState = MutableStateFlow(ChannelAddingUiState())
    val channelAddState = _channelAddState.asStateFlow()

    init {
        loadChannels()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            _uiState.value = when (val result = getChannelsUseCase()) {
                is Outcome.Success -> HomeUiState(
                    channels = result.value,
                    isLoading = false
                )

                is Outcome.Failure -> HomeUiState(
                    channels = emptyList(),
                    isLoading = false,
                    error = result.error.message()
                )
            }
        }
    }

    fun addChannel(channelId: String) {
        viewModelScope.launch {
            _channelAddState.update {
                it.copy(isLoading = true, error = null)
            }

            when (val result = addChannelUseCase(channelId)) {
                is Outcome.Success -> {
                    _channelAddState.value = ChannelAddingUiState(
                        isSuccess = true,
                        isLoading = false
                    )
                    loadChannels()
                }

                is Outcome.Failure -> {
                    _channelAddState.value = ChannelAddingUiState(
                        isSuccess = false,
                        isLoading = false,
                        error = result.error.message()
                    )
                }
            }
        }
    }
}
