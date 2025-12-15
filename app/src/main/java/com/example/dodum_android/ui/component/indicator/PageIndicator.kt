package com.example.dodum_android.ui.component.indicator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun PageIndicator(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val maxButtons = 5

    // 버튼 범위 계산
    val start = maxOf(1, currentPage - 2)
    val end = minOf(totalPages, start + maxButtons - 1)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // ◀ 이전
        Text(
            text = "◀",
            fontSize = 20.sp,
            color = if (currentPage == 1) Color.LightGray else Color.Black,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .clickable(enabled = currentPage != 1) {
                    onPageChange(currentPage - 1)
                }
        )

        // 페이지 번호 (최대 5개)
        for (page in start..end) {
            Text(
                text = page.toString(),
                fontSize = 20.sp,
                color = if (page == currentPage) MainColor else Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .clickable {
                        onPageChange(page)
                    }
            )
        }

        // ▶ 다음
        Text(
            text = "▶",
            fontSize = 20.sp,
            color = if (currentPage == totalPages) Color.LightGray else Color.Black,
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .clickable(enabled = currentPage != totalPages) {
                    onPageChange(currentPage + 1)
                }
        )
    }
}
