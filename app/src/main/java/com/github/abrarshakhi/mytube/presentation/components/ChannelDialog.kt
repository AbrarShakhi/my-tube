package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.presentation.MainViewModel
import com.github.abrarshakhi.mytube.presentation.state.AddChannelState
import com.github.abrarshakhi.mytube.presentation.state.RemoveChannelState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelDialog(viewModel: MainViewModel) {
    val addChannelState by viewModel.addChannelState.collectAsState()
    val removeChannelState by viewModel.removeChannelState.collectAsState()

    when (val state = addChannelState) {
        is AddChannelState.Found -> {
            val isNotLoading = removeChannelState !is RemoveChannelState.Loading

            BasicAlertDialog(onDismissRequest = {}) {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = state.channel.title)
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = { viewModel.defaultState() }
                            ) { Text("Cancel") }

                            TextButton(
                                onClick = { viewModel.showSheet() },
                                enabled = isNotLoading
                            ) { Text("Edit") }

                            TextButton(
                                onClick = { viewModel.removeChannel(state.channel) },
                                enabled = isNotLoading
                            ) { Text("Remove") }
                        }
                    }
                }
            }
        }

        else -> Unit
    }
}
