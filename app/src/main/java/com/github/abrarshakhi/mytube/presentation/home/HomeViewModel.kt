package com.github.abrarshakhi.mytube.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.usecase.AddChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.FindChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.GetChannelsUseCase
import com.github.abrarshakhi.mytube.domain.usecase.RemoveChannelUseCase
import com.github.abrarshakhi.mytube.domain.usecase.ShowFilterOutcomeUseCase
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelState
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelWithFilterState
import com.github.abrarshakhi.mytube.presentation.home.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.home.state.HomeState
import com.github.abrarshakhi.mytube.presentation.home.state.RemoveChannelState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val showFilterOutcomeUseCase: ShowFilterOutcomeUseCase,
    private val getChannelsUseCase: GetChannelsUseCase,
    private val findChannelUseCase: FindChannelUseCase,
    private val addChannelUseCase: AddChannelUseCase,
    private val removeChannelUseCase: RemoveChannelUseCase
) : ViewModel() {

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

    private val _infoState = MutableStateFlow("")
    val infoState = _infoState.asStateFlow()

    private val _handleTextState = MutableStateFlow("@")
    val handleTextState = _handleTextState.asStateFlow()
    fun setHandleText(text: String) {
        _handleTextState.update { text }
    }

    private val _filterTextState = MutableStateFlow("")
    val filterTextState = _filterTextState.asStateFlow()
    fun setFilterText(text: String) {
        _filterTextState.update { text }
        _infoState.update {
            showFilterOutcomeUseCase(
                regex = _filterTextState.value,
                contains = _filterShouldContainState.value
            )
        }

    }

    private val _filterShouldContainState = MutableStateFlow(true)
    val filterShouldContainState = _filterShouldContainState.asStateFlow()
    fun setFilterShouldContain(boolean: Boolean) {
        _filterShouldContainState.update { boolean }
        _infoState.update {
            showFilterOutcomeUseCase(
                regex = _filterTextState.value,
                contains = _filterShouldContainState.value
            )
        }
    }

    private val _addChannelState = MutableStateFlow<AddChannelState>(AddChannelState.Cleared)
    val addChannelState = _addChannelState.asStateFlow()
    fun findChannel(handle: String) {
        _addChannelState.update { AddChannelState.Loading }
        viewModelScope.launch {
            findChannelUseCase(handle.trim()).onSuccess { channel ->
                selectChannel(channel)
            }.onFailure { throwable ->
                _addChannelState.update {
                    AddChannelState.Error(
                        throwable.message ?: "Unknown Error Occurred"
                    )
                }
            }
        }
    }

    fun selectChannel(channel: Channel) {
        _addChannelState.update { AddChannelState.Found(channel) }
    }

    private val _addChannelWithFilterState =
        MutableStateFlow<AddChannelWithFilterState>(AddChannelWithFilterState.Cleared)
    val addChannelWithFilterState = _addChannelWithFilterState.asStateFlow()
    fun addChannel(channel: Channel, regex: String, switchChecked: Boolean) {
        _addChannelWithFilterState.update { AddChannelWithFilterState.Loading }
        viewModelScope.launch {
            addChannelUseCase(
                channel.copy(
                    filter = channel.filter.copy(
                        contains = switchChecked, regex = regex
                    )
                )
            ).onSuccess { channelName ->
                _addChannelWithFilterState.update { AddChannelWithFilterState.Success(channelName) }
                loadChannels()
                defaultState()
            }.onFailure { throwable ->
                _addChannelWithFilterState.update {
                    AddChannelWithFilterState.Error(
                        throwable.message ?: "Unknown Error Occurred"
                    )
                }
            }
        }
    }

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Hidden)
    val homeState = _homeState.asStateFlow()
    fun showSheet() {
        _homeState.update { HomeState.Sheet }
    }

    fun showDialog() {
        _homeState.update { HomeState.Dialog }
    }

    fun defaultState() {
        _homeState.update { HomeState.Hidden }
        _addChannelState.update { AddChannelState.Cleared }
        _addChannelWithFilterState.update { AddChannelWithFilterState.Cleared }
        _handleTextState.update { "@" }
        _filterTextState.update { "" }
        setFilterShouldContain(true)
    }

    private val _removeChannelState =
        MutableStateFlow<RemoveChannelState>(RemoveChannelState.Success)
    val removeChannelState = _removeChannelState.asStateFlow()
    fun removeChannel(channel: Channel) {
        viewModelScope.launch {
            removeChannelUseCase(channel).onSuccess {
                loadChannels()
            }
            defaultState()
        }
    }

    init {
        loadChannels()
        setFilterShouldContain(true)
    }
}
