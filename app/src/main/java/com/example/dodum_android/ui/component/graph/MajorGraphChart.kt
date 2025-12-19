package com.example.dodum_android.ui.component.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.network.major.Graph

@Composable
fun MajorGraphChart(graph: Graph) {
    val data = listOf(
        Triple("Web", graph.web, Color(0xFF26874E)),
        Triple("Server", graph.server, Color(0xFF64B5F6)),
        Triple("iOS", graph.ios, Color(0xFF8EF2B7)),
        Triple("Android", graph.android, Color(0xFF64B5F6))
    )

    val maxScore = data.maxOf { it.second }.coerceAtLeast(1)
    val maxHeight = 100.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        data.forEach { (label, score, color) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(maxHeight * (score / maxScore.toFloat()))
                        .background(color, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = label, fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}