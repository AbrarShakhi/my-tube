package com.github.abrarshakhi.mytube.presentation.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.presentation.VideoActivity
import com.github.abrarshakhi.mytube.presentation.components.ChannelItem
import com.github.abrarshakhi.mytube.ui.theme.MyTubeTheme

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MyTubeTheme {
        HomeScreen(showDialog = true)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    showDialog: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsState()
    val channelAddState by viewModel.channelAddState.collectAsState()

    var showDialog by remember { mutableStateOf(showDialog) }
    LaunchedEffect(channelAddState.isSuccess) {
        if (channelAddState.isSuccess) {
            showDialog = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("YouTube Channels") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Channel")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                LoadingContent(padding)
            }

            uiState.error != null -> {
                ErrorContent(
                    message = uiState.error!!,
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                ChannelList(
                    channels = uiState.channels,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }

    if (showDialog) {
        AddChannelDialog(
            isLoading = channelAddState.isLoading,
            onDismiss = { showDialog = false },
            onAdd = { handle -> viewModel.addChannel(handle) }
        )
    }
}


@Composable
fun AddChannelDialog(
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var handle by remember { mutableStateOf("@") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Channel") },
        text = {
            Column {
                OutlinedTextField(
                    value = handle,
                    onValueChange = { handle = it },
                    label = { Text("@ Channel Handle") },
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = !isLoading,
                onClick = { onAdd(handle) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun LoadingContent(paddingValues: PaddingValues) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}


@Composable
fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = message, color = androidx.compose.material3.MaterialTheme.colorScheme.error)
    }
}

@Composable
fun ChannelList(
    channels: List<Channel>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        items(channels) { channel ->
            ChannelItem(channel) {
                context.startActivity(Intent(context, VideoActivity::class.java))
            }
        }
    }
}
