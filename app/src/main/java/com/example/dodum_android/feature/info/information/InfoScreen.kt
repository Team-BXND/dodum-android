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
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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

    val density = LocalDensity.current

    var menuXdp by remember { mutableStateOf(0.dp) }
    var menuYdp by remember { mutableStateOf(0.dp) }

    val title = "해커톤 행사 건"
    val userName = "3101 홍길동"
    val date = "2025.11.25"
    val content = "자세한 대회내용 자세한 대회내용 자세한 대회내용 자세한 대회내용 자세한 대회내용"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            TopBar(navController)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
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
                            .onGloballyPositioned { coordinates ->
                                val pos = coordinates.positionInWindow()
                                menuXdp = with(density) { pos.x.toDp() }
                                menuYdp = with(density) { pos.y.toDp() }
                            }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { menuOpen = true }
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
                        Text(userName, fontSize = 12.sp, color = Color(0xFF1B1B1B))
                        Text(date, fontSize = 12.sp, color = Color(0xFFADADAD))
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

        if (menuOpen) {

            // 바깥 클릭 시 닫기 (이펙트 제거 포함)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { menuOpen = false }
            )

            val popupWidth = 85.dp
            val popupHeight = 48.dp
            val hGap = 8.dp
            val vGap = 6.dp

            Box(
                modifier = Modifier
                    .absoluteOffset(
                        x = menuXdp - popupWidth - hGap,
                        y = menuYdp + vGap
                    )
                    .size(popupWidth, popupHeight)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFADADAD), RoundedCornerShape(12.dp))
                    .padding(vertical = 6.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        "수정",
                        fontSize = 13.sp,
                        color = Color(0xFF1B1B1B),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { menuOpen = false }
                    )

                    Divider(color = Color(0xFFE3E3E3), thickness = 1.dp)

                    Text(
                        "삭제",
                        fontSize = 13.sp,
                        color = Color(0xFF1B1B1B),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { menuOpen = false }
                    )
                }
            }
        }
    }
}