package com.github.abrarshakhi.mytube.presentation.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.abrarshakhi.mytube.presentation.VideoActivity
import com.github.abrarshakhi.mytube.presentation.components.AddChannelContent
import com.github.abrarshakhi.mytube.presentation.components.AddFilterContent
import com.github.abrarshakhi.mytube.presentation.components.ChannelItem
import com.github.abrarshakhi.mytube.presentation.components.ErrorContent
import com.github.abrarshakhi.mytube.presentation.components.LoadingContent
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelState
import com.github.abrarshakhi.mytube.presentation.home.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.home.state.SheetState


@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val sheetStateUi by viewModel.sheetState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val channelListStateUi by viewModel.channelListState.collectAsState()
    val addChannelStateUi by viewModel.addChannelState.collectAsState()
    val addChannelWithFilterStateUi by viewModel.addChannelWithFilterState.collectAsState()

    val handleTextState by viewModel.handleTextState.collectAsState()
    val filterTextState by viewModel.filterTextState.collectAsState()
    val filterShouldContainState by viewModel.filterShouldContainState.collectAsState()
    val infoState by viewModel.infoState.collectAsState()

    val dismissSheet = { viewModel.defaultSheet() }
    LaunchedEffect(addChannelWithFilterStateUi) {
        dismissSheet()
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("MyTube") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.showSheet() }) {
            Icon(Icons.Default.Add, contentDescription = "Add Channel")
        }
    }) { paddingValues ->
        val context = LocalContext.current
        when (channelListStateUi) {
            is ChannelListState.Loading -> LoadingContent(
                Modifier.padding(paddingValues).fillMaxSize()
            )

            is ChannelListState.Error -> ErrorContent(
                (channelListStateUi as ChannelListState.Error).message,
                Modifier.padding(paddingValues)
            )

            is ChannelListState.Success ->
                LazyColumn(Modifier.padding(paddingValues)) {
                    items((channelListStateUi as ChannelListState.Success).channels) { channel ->
                        ChannelItem(
                            channel = channel,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                context.startActivity(Intent(context, VideoActivity::class.java))
                            },
                            onLongClick = {
                                viewModel.selectChannel(channel)
                                viewModel.showSheet()
                            })
                    }
                }
        }
    }

    if (sheetStateUi is SheetState.Hidden) {
        return
    }

    ModalBottomSheet(
        onDismissRequest = dismissSheet,
        sheetState = sheetState
    ) {
        Column {
            AddChannelContent(
                handle = handleTextState,
                onTextFieldValueChange = { viewModel.setHandleText(it) },
                onButtonClick = { viewModel.findChannel(handleTextState) },
                addChannelState = addChannelStateUi
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (addChannelStateUi is AddChannelState.Found) {
                AddFilterContent(
                    filter = filterTextState,
                    switchChecked = filterShouldContainState,
                    onTextFieldValueChange = { viewModel.setFilterText(it) },
                    onSwitchCheckedChange = { viewModel.setFilterShouldContain(it) },
                    onButtonClick = {
                        viewModel.addChannel(
                            (addChannelStateUi as AddChannelState.Found).channel,
                            regex = filterTextState,
                            switchChecked = filterShouldContainState
                        )
                    },
                    infoText = infoState,
                    addChannelWithFilterState = addChannelWithFilterStateUi
                )
            }
        }
    }
}

