package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.presentation.home.state.AddChannelWithFilterState

@Composable
fun AddFilterContent(
    filter: String,
    switchChecked: Boolean,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    onSwitchCheckedChange: (Boolean) -> Unit,
    infoText: String,
    addChannelWithFilterState: AddChannelWithFilterState
) {
    val enabled = addChannelWithFilterState !is AddChannelWithFilterState.Loading

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) { Text(text = "Notification filter", style = MaterialTheme.typography.titleLarge) }
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = switchChecked,
            onCheckedChange = onSwitchCheckedChange,
            enabled = enabled,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = filter,
            onValueChange = onTextFieldValueChange,
            label = { Text("RegEx") },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
        )
        ErrorContent(
            text = if (addChannelWithFilterState is AddChannelWithFilterState.Error) {
                addChannelWithFilterState.message
            } else {
                ""
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = infoText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (enabled) {
                Button(
                    onClick = onButtonClick
                ) {
                    Text(text = "Add filter")
                }
            } else {
                CircularProgressIndicator()
            }
        }

    }
}
