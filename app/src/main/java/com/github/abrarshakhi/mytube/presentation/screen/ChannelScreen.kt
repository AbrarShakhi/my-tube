package com.github.abrarshakhi.mytube.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.abrarshakhi.mytube.presentation.MainViewModel
import com.github.abrarshakhi.mytube.presentation.components.BottomSheet
import com.github.abrarshakhi.mytube.presentation.components.ChannelDialog
import com.github.abrarshakhi.mytube.presentation.components.ChannelItem
import com.github.abrarshakhi.mytube.presentation.components.ErrorContent
import com.github.abrarshakhi.mytube.presentation.components.LoadingContent
import com.github.abrarshakhi.mytube.presentation.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.state.ChannelScreenState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelScreen(
    onNavigateToChannel: (String) -> Unit,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
) {
    val channelScreenState by viewModel.channelScreenState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val channelListState by viewModel.channelListState.collectAsState()
    LaunchedEffect(channelScreenState) {
        if (channelScreenState is ChannelScreenState.Sheet) {
            sheetState.show()
        }
    }

    LocalContext.current
    when (val state = channelListState) {
        is ChannelListState.Loading -> LoadingContent(
            Modifier.padding(paddingValues).fillMaxSize()
        )

        is ChannelListState.Error -> ErrorContent(
            state.message,
            Modifier.padding(paddingValues)
        )

        is ChannelListState.Success ->
            LazyColumn(Modifier.padding(paddingValues)) {
                items(state.channels) { channel ->
                    ChannelItem(
                        channel = channel, modifier = Modifier.padding(8.dp), onClick = {
                            onNavigateToChannel(channel.channelId)
                        }, onLongClick = {
                            viewModel.selectChannel(channel)
                            viewModel.showDialog()
                        }
                    )
                }
            }
    }
    when (channelScreenState) {
        is ChannelScreenState.Sheet -> BottomSheet(viewModel, sheetState)
        is ChannelScreenState.Dialog -> ChannelDialog(viewModel)
        ChannelScreenState.Hidden -> {}
    }
}