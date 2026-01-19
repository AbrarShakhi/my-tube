package com.github.abrarshakhi.mytube.presentation.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.mytube.domain.model.Channel

@Composable
fun ChannelItem(
    channel: Channel,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                enabled = clickable
            )
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(14.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Channel Thumbnail
                channel.thumbnail?.let { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Channel Thumbnail",
                        modifier = Modifier
                            .size(48.dp)           // Perfect circular size
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (channel.hasNewUpload) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(10.dp)
                                    .background(Color.Red, shape = CircleShape)
                            )
                        }
                        Text(
                            text = channel.title,
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
    }
}
