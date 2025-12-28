package com.github.abrarshakhi.mytube.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.domain.model.Channel


@Composable
fun ChannelItem(channel: Channel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(14.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = CenterVertically) {
                if (channel.hasNewUpload) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(10.dp)
                            .background(Color.Red, shape = CircleShape)
                    )
                }
                Text(
                    text = channel.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = channel.lastUploadedSince,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ChannelItemPreview() {
    MaterialTheme {
        ChannelItem(
            Channel(
                channelId = "",
                name = "GoodTimesWithScar",
                hasNewUpload = true,
                lastUploadedSince = "32 minutes ago"
            ),
            {}
        )
    }
}