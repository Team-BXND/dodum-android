package com.example.dodum_android.ui.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.dodum_android.R
import com.example.dodum_android.network.contest.ContestData

@Composable
fun ContestCard(
    item: ContestData,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. 이미지 영역 (파란색 배경에 로고)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(item.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${item.title} logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.dodam_logo)
                )
            }

            // 2. 텍스트 정보 영역
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                // 대회명
                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 상세 정보 (전화번호, 일시, 장소 등)
                val infoText = """
                    이메일: ${item.email}
                    전화번호: ${item.phone}
                    일시: ${item.time}
                    장소: ${item.place}
                """.trimIndent()

                Text(
                    text = infoText,
                    fontSize = 16.sp,
                    color = Color(0xFF555555),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 본문 미리보기 (간략히)
                Text(
                    text = item.content,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}