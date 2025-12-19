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
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopBar

@Composable
fun InfoScreen(
    navController: NavController,
    infoId: Int
) {
    val viewModel: InfoViewModel = hiltViewModel()
    val detail by viewModel.detail.collectAsState()

    var menuOpen by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    var menuXdp by remember { mutableStateOf(0.dp) }
    var menuYdp by remember { mutableStateOf(0.dp) }

    LaunchedEffect(infoId) {
        viewModel.loadDetail(infoId)
    }

    if (detail == null) return

    Box(modifier = Modifier.fillMaxSize()) {

        Column {

            TopBar(navController)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {

                /** 제목 + 메뉴 */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = detail!!.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B1B1B)
                    )

                    Column(
                        modifier = Modifier
                            .onGloballyPositioned {
                                val pos = it.positionInWindow()
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

                Spacer(Modifier.height(20.dp))

                /** 작성자 + 날짜 */
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
                            text = detail!!.author,
                            fontSize = 12.sp,
                            color = Color(0xFF1B1B1B)
                        )
                        Text(
                            text = detail!!.date,
                            fontSize = 12.sp,
                            color = Color(0xFFADADAD)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
                Divider(color = Color(0xFFE3E3E3))
                Spacer(Modifier.height(16.dp))

                /** 이미지 */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.LightGray, RoundedCornerShape(14.dp))
                )

                Spacer(Modifier.height(20.dp))

                /** 내용 */
                Text(
                    text = detail!!.content,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1B1B1B)
                )
            }
        }

        /** 메뉴 팝업 */
        if (menuOpen) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { menuOpen = false }
            )

            Box(
                modifier = Modifier
                    .absoluteOffset(
                        x = menuXdp - 93.dp,
                        y = menuYdp + 8.dp
                    )
                    .size(93.dp, 52.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFADADAD), RoundedCornerShape(12.dp))
            ) {
                Column {

                    Text(
                        "수정",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable {
                                menuOpen = false
                                navController.navigate(NavGroup.IWRITE)
                            },
                        fontSize = 13.sp
                    )

                    Divider(color = Color(0xFFE3E3E3))

                    Text(
                        "삭제",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable {
                                menuOpen = false
                                // 삭제 로직 연결 가능
                            },
                        fontSize = 13.sp,
                        color = Color.Red
                    )
                }
            }
        }
    }
}