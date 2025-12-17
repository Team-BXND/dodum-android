package com.example.dodum_android.feature.contest.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.feature.contest.ContestViewModel
import com.example.dodum_android.ui.component.bar.TopBar

@Composable
fun ContestDetailScreen(
    navController: NavController,
    contestId: Int,
    viewModel: ContestViewModel = hiltViewModel()
) {
    LaunchedEffect(contestId) {
        viewModel.loadContestDetail(contestId)
    }

    val detail by viewModel.contestDetail.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    val canEdit = userRole == "ADMIN" // 수정/삭제 권한 (예시)

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        if (detail == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val data = detail!!
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // 상단 헤더 (제목 + 알림/메뉴)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Row {
                        // 알림 토글 버튼
                        IconButton(onClick = {
                            viewModel.toggleAlert(data.id)
                            val msg = if (data.isAlertActive) "알림이 꺼졌습니다." else "알림이 설정되었습니다."
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = if (data.isAlertActive) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                                contentDescription = "Alert",
                                tint = if (data.isAlertActive) Color(0xFFFFD700) else Color.Gray // 켜지면 노란색 등
                            )
                        }

                        // 더보기 메뉴 (수정/삭제)
                        if (canEdit) {
                            Box {
                                IconButton(onClick = { showMenu = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Gray)
                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("수정") },
                                        onClick = {
                                            showMenu = false
                                            // 수정 화면으로 이동 (ID 포함)
                                            navController.navigate("contestModify/${data.id}")
                                        }
                                    )
                                    DropdownMenuItem(text = { Text("삭제") }, onClick = {
                                        showMenu = false
                                        viewModel.deleteContest(data.id) {
                                            Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        }
                                    })
                                }
                            }
                        }
                    }
                }

                // 이미지 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .height(330.dp)
                        .background(Color(0xFF0084FF), RoundedCornerShape(14.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dodam_logo),
                        contentDescription = "Contest Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 상세 정보
                Column(
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    val infoText = """
                        이메일: ${data.email}
                        전화번호: ${data.phone}
                        일시: ${data.time}
                        장소: ${data.place}
                    """.trimIndent()

                    Text(
                        text = infoText,
                        fontSize = 18.sp,
                        color = Color(0xFF1B1B1B),
                        lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFE3E3E3))
                    Spacer(modifier = Modifier.height(16.dp))

                    // 본문
                    Text(
                        text = data.subTitle,
                        fontSize = 18.sp,
                        color = Color(0xFF1B1B1B),
                        lineHeight = 26.sp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}