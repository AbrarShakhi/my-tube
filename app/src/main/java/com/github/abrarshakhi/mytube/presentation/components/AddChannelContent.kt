package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.presentation.state.AddChannelState

@Composable
fun AddChannelContent(
    handle: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    addChannelState: AddChannelState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        when (addChannelState) {
            is AddChannelState.Found -> ChannelItem(
                channel = addChannelState.channel,
                clickable = false,
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
}


@Composable
private fun AddChannel(
    handle: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    addChannelState: AddChannelState
) {
    val enabled = addChannelState !is AddChannelState.Loading

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) { Text(text = "Insert channel handle", style = MaterialTheme.typography.titleLarge) }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = handle,
        onValueChange = onTextFieldValueChange,
        label = { Text("@ChannelHandle") },
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1
    )
    ErrorContent(
        text = if (addChannelState is AddChannelState.Error) {
            addChannelState.message
        } else {
            ""
        }
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        if (enabled) {
            Button(
                onClick = onButtonClick,
                modifier = Modifier.padding(4.dp)
            ) { Text(text = "Find Channel") }
        } else {
            CircularProgressIndicator()
        }
    }
}
