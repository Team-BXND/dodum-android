package com.example.dodum_android.ui.component.info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun CategoryBadge(category: String) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFFF7531),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 30.dp, vertical = 2.dp)
    ) {
        Text(
            text = category,
            fontSize = 16.sp,
            letterSpacing = (-0.05).em,
            color = Color(0xFFFF7531)
        )
    }
}
