package com.example.dodum_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.dodum_android.network.profile.MyPost
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MyPostItem(post: MyPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomCard(padding = PaddingValues(horizontal = 40.dp, vertical = 10.dp)) {
            Column {
                AsyncImage(
                    model = post.image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(140.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatDate(post.date),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)
        dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
    } catch (e: Exception) {
        dateString
    }
}