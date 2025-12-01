package com.example.dodum_android.ui.component.major

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun Multiple(
    question: String,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val items = listOf(
        // width, height, borderColor, borderWidth, backgroundColor
        Triple(27.dp, 27.dp, Color(0xFF19C5FF)),          // selected: filled + check
        Triple(24.dp, 24.dp, Color(0xFF19C5FF)),          // only border
        Triple(21.dp, 21.dp, Color(0xFFADADAD)),  // gray border
        Triple(24.dp, 24.dp, Color(0xFFCA76FF)),  // purple border
        Triple(27.dp, 27.dp, Color(0xFFCA76FF)),  // purple border
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(8.dp)
                .background(MainColor, shape = RoundedCornerShape(16.dp))
        )

        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = MainColor,
                    clip = false
                )
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 23.dp, top = 12.dp)
            ) {

                Text(
                    text = question,
                    fontSize = 16.sp,
                )
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items.forEachIndexed { index, item ->

                            val (sizeW, sizeH, borderColor) = item

                            Box(
                                modifier = Modifier
                                    .size(sizeW, sizeH)
                                    .background(
                                        color = if (selectedIndex == index) borderColor else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = borderColor,
                                        shape = CircleShape
                                    )
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { onSelect(index) },
                                contentAlignment = Alignment.Center
                            ) {
                                if (selectedIndex == index) {
                                    // 체크 표시(✓)
                                    Text(
                                        text = "✓",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .padding(top = 4.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "동의함",
                            fontSize = 12.sp,
                            color = Color(0xFF19C5FF)
                        )
                        Text(
                            "동의하지 않음",
                            fontSize = 12.sp,
                            color = Color(0xFFCA76FF)
                        )
                    }
                }
            }
        }
    }
}
