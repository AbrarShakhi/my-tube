package com.github.abrarshakhi.mytube.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.mytube.domain.usecase.FindChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.GetChannelsUseCase
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelState
import com.github.abrarshakhi.mytube.presentation.home.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.home.state.SheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChannelsUseCase: GetChannelsUseCase,
    private val findChannelUseCase: FindChannelUseCase
) : ViewModel() {

    private val _sheetState = MutableStateFlow<SheetState>(SheetState.Hidden)
    val sheetState = _sheetState.asStateFlow()
    fun showSheet() {
        _sheetState.update { SheetState.Visible }
    }

    fun defaultSheet() {
        _sheetState.update { SheetState.Hidden }
        _addChannelState.update { AddChannelState.Cleared }
    }

    private val _channelListState = MutableStateFlow<ChannelListState>(ChannelListState.Loading)
    val channelListState = _channelListState.asStateFlow()
    fun loadChannels() {
        _channelListState.update { ChannelListState.Loading }
        viewModelScope.launch {
            getChannelsUseCase().onSuccess { channels ->
                _channelListState.update { ChannelListState.Success(channels) }
            }.onFailure { throwable ->
                _channelListState.update {
                    ChannelListState.Error(
                        throwable.message ?: "Unknown Error Occurred"
                    )
                }
            }
        }
    }

    init {
        loadChannels()
    }

    private val _addChannelState = MutableStateFlow<AddChannelState>(AddChannelState.Cleared)
    val addChannelState = _addChannelState.asStateFlow()
    fun findChannel(handle: String) {
        _addChannelState.update { AddChannelState.Loading }
        viewModelScope.launch {
            findChannelUseCase(handle).onSuccess { channel ->
                _addChannelState.update { AddChannelState.Found(channel) }
            }.onFailure { throwable ->
                _addChannelState.update {
                    AddChannelState.Error(
                        throwable.message ?: "Unknown Error Occurred"
                    )
                }
            }
        }
    }
}
