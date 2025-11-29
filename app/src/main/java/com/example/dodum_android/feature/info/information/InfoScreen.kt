package com.example.dodum_android.feature.info.information

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopBar

@Composable
fun InfoScreen(
    navController: NavController,
) {

    val viewModel: InfoViewModel = hiltViewModel()

    var menuOpen by remember { mutableStateOf(false) }

    val title = "해커톤 행사 건"
    val userName = "3101 홍길동"
    val date = "2025.11.25"
    val content = "자세한 대회내용 자세한 대회내용 자세한 대회내용 자세한 대회내용 자세한 대회내용"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        /** 메인 콘텐츠 */
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(navController)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .shadow(
                        elevation = 4.dp,
                        spotColor = Color(0x0D000000),
                        ambientColor = Color(0x0D000000)
                    )
                    .padding(24.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B1B1B)
                    )

                    Column(
                        modifier = Modifier
                            .clickable { menuOpen = true }
                            .padding(end = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(Color(0xFFADADAD), CircleShape)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.Gray, CircleShape)
                    )

                    Column {
                        Text(
                            text = userName,
                            fontSize = 12.sp,
                            color = Color(0xFF1B1B1B)
                        )
                        Text(
                            text = date,
                            fontSize = 12.sp,
                            color = Color(0xFFADADAD)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Divider(color = Color(0xFFE3E3E3))

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.LightGray, RoundedCornerShape(14.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = content,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1B1B1B)
                )
            }
        }

        /** 팝업은 Box의 형제로 분리! */
        if (menuOpen) {

            // 바깥 클릭 → 닫기
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { menuOpen = false }
            )

            // 실제 팝업
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-20).dp, y = 110.dp) // TopBar 기준 정확한 위치 조절
                    .size(width = 65.dp, height = 41.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFADADAD), RoundedCornerShape(12.dp))
                    .padding(vertical = 6.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "수정",
                        fontSize = 12.sp,
                        color = Color(0xFFADADAD),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                menuOpen = false
                                // 수정 클릭 로직
                            }
                    )

                    Text(
                        text = "삭제",
                        fontSize = 12.sp,
                        color = Color(0xFFADADAD),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                menuOpen = false
                                // 삭제 클릭 로직
                            }
                    )
                }
            }
        }
    }
}
