package com.github.abrarshakhi.mytube.presentation.home

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.abrarshakhi.mytube.presentation.VideoActivity
import com.github.abrarshakhi.mytube.presentation.components.BottomSheet
import com.github.abrarshakhi.mytube.presentation.components.ChannelDialog
import com.github.abrarshakhi.mytube.presentation.components.ChannelItem
import com.github.abrarshakhi.mytube.presentation.components.ErrorContent
import com.github.abrarshakhi.mytube.presentation.components.LoadingContent
import com.github.abrarshakhi.mytube.presentation.home.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.home.state.HomeState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.homeState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val channelListState by viewModel.channelListState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("MyTube") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.showSheet() }) {
            Icon(Icons.Default.Add, contentDescription = "Add Channel")
        }
    }) { paddingValues ->
        val context = LocalContext.current
        when (channelListState) {
            is ChannelListState.Loading -> LoadingContent(
                Modifier.padding(paddingValues).fillMaxSize()
            )

            is ChannelListState.Error -> ErrorContent(
                (channelListState as ChannelListState.Error).message,
                Modifier.padding(paddingValues)
            )

            is ChannelListState.Success ->
                LazyColumn(Modifier.padding(paddingValues)) {
                    items((channelListState as ChannelListState.Success).channels) { channel ->
                        ChannelItem(
                            channel = channel,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                context.startActivity(Intent(context, VideoActivity::class.java))
                            },
                            onLongClick = {
                                viewModel.selectChannel(channel)
                                viewModel.showDialog()
                            }
                        )
                    }
                }
        }
    }
    when (homeState) {
        is HomeState.Sheet -> BottomSheet(viewModel, sheetState)
        is HomeState.Dialog -> ChannelDialog(viewModel)
        HomeState.Hidden -> {}
    }
}