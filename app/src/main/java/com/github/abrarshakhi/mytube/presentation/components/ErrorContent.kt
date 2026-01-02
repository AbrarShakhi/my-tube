package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorContent(modifier: Modifier, errorMessage: String) {
    Column(modifier = modifier) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
    }
}