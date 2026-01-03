package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelState

@Composable
fun AddChannelContent(
    handle: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    addChannelState: AddChannelState
) {
    when (addChannelState) {
        is AddChannelState.Found -> ChannelItem(
            channel = addChannelState.channel,
            onClick = {},
            onLongClick = {}
        )

        else -> {
            AddChannel(
                handle = handle,
                onTextFieldValueChange = onTextFieldValueChange,
                onButtonClick = onButtonClick,
                addChannelState = addChannelState
            )
        }
    }
}


@Composable
private fun AddChannel(
    handle: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    addChannelState: AddChannelState
) {
    val enabled = addChannelState !is AddChannelState.Loading

    Text(text = "Channel", style = MaterialTheme.typography.bodyMedium)
    OutlinedTextField(
        value = handle,
        onValueChange = onTextFieldValueChange,
        label = { Text("@ChannelHandle") },
        enabled = enabled
    )
    if (addChannelState is AddChannelState.Error) {
        ErrorContent(text = addChannelState.message)
    }
    Button(onClick = onButtonClick, enabled = enabled) {
        Text(text = "check channel")
    }
}
