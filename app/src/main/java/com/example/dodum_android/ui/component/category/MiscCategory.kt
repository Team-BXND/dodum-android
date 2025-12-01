package com.example.dodum_android.ui.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun MiscCategory(
    selected: String,
    onSelectedChange: (String) -> Unit
) {
    val categories = listOf("전체", "학교지원", "개발도구", "강의추천")

    Row(
        modifier = Modifier
            .padding(start = 13.dp, top = 108.dp)
            .height(28.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        categories.forEach { category ->
            val isSelected = selected == category

            Column(
                modifier = Modifier
                    .clickable { onSelectedChange(category) },
            ) {
                Text(
                    text = category,
                    fontSize = 20.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    letterSpacing = (-0.05).em,
                    color = if (isSelected) Color(0xFF26874E) else Color(0xFFADADAD)
                )

                // 선택된 탭 밑줄
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(34.dp)   // Figma width
                            .height(1.dp)
                            .background(Color(0xFF26874E))
                    )
                } else {
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        }
    }
}