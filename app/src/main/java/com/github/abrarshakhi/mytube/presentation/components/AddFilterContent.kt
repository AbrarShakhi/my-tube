package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.abrarshakhi.mytube.domain.model.Channel

@Composable
fun AddFilterContent(
    filter: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Text(text = "Filters", style = MaterialTheme.typography.bodyMedium)
    OutlinedTextField(
        value = filter,
        onValueChange = onTextFieldValueChange,
        label = { Text("filter") }
    )
    ErrorContent(text = "error")
    Button(onClick = onButtonClick) {
        Text(text = "add filter")
    }
}