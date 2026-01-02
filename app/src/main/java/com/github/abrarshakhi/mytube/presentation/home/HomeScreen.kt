package com.github.abrarshakhi.mytube.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.abrarshakhi.mytube.presentation.components.LoadingContent
import com.github.abrarshakhi.mytube.presentation.home.state.ChannelListState
import com.github.abrarshakhi.mytube.presentation.home.state.SheetState


@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val sheetStateUi = viewModel.sheetState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val channelListStateUi = viewModel.channelListState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MyTube") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showSheet() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Channel")
            }
        }
    ) { paddingValues ->
        when (channelListStateUi.value) {
            is ChannelListState.Loading ->
                LoadingContent(paddingValues)

            is ChannelListState.Error ->
                TODO("SHOW ERROR")

            is ChannelListState.Success ->
                TODO("SHOW CHANNELS")

        }
    }

    if (sheetStateUi.value is SheetState.Visible) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.defaultSheet() },
            modifier = Modifier,
            sheetState = sheetState
        ) {

        }
    }
}