package com.example.dodum_android.feature.archive.detail

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ArchiveDetailScreen(
    navController: NavController,
    archiveId: Long,
    viewModel: ArchiveDetailViewModel = hiltViewModel()
) {
    // 화면 진입 시 상세 데이터 로드
    LaunchedEffect(archiveId) {
        viewModel.loadDetail(archiveId)
    }

    val detail by viewModel.detail.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val currentUserId by viewModel.currentUserId.collectAsState()

    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        if (detail == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MainColor)
            }
        } else {
            val data = detail!!

            // TODO: 실제 API 연동 시 data.authorId와 currentUserId를 비교해야 함.
            // 현재는 더미 데이터 또는 임시 로직으로 처리.
            // val isAuthor = data.authorId == currentUserId
            val isAuthor = true // 테스트를 위해 항상 true로 설정 (실제 구현 시 위 주석 해제)

            val isAdminOrTeacher = userRole == "ADMIN" || userRole == "TEACHER"

            val canEdit = isAuthor
            val canDelete = isAuthor || isAdminOrTeacher

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 15.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    // 텍스트 정보 (좌측 정렬)
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(end = 40.dp)
                    ) {
                        Text(
                            text = data.title,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            lineHeight = 40.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (data.subtitle.isNotEmpty()) {
                            Text(
                                text = data.subtitle,
                                fontSize = 14.sp,
                                color = Color(0xFFADADAD),
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Text(
                            text = "TEAM - ${data.teamname}",
                            fontSize = 14.sp,
                            color = Color(0xFFADADAD),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // 메뉴 버튼 (권한이 있을 때만 표시)
                    if (canDelete || canEdit) {
                        Box(modifier = Modifier.align(Alignment.TopEnd)) {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Menu",
                                    tint = Color(0xFFADADAD)
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                if (canEdit) {
                                    DropdownMenuItem(
                                        text = { Text("수정") },
                                        onClick = {
                                            showMenu = false
                                            // 수정 화면으로 이동 (NavGroup.ArchiveModify 사용)
                                            // 주의: route 정의가 "archiveModify/{archiveId}" 이므로 ID를 넣어줌
                                            navController.navigate("archiveModify/$archiveId")
                                        }
                                    )
                                }
                                if (canDelete) {
                                    DropdownMenuItem(
                                        text = { Text("삭제") },
                                        onClick = {
                                            showMenu = false
                                            viewModel.deleteArchive(archiveId) {
                                                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                                navController.popBackStack() // 목록으로 돌아가기
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                DashedDivider(
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )

                Text(
                    text = data.content,
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    color = Color(0xFF1B1B1B),
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.dodam_view),
                    contentDescription = "Project Detail Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .wrapContentHeight()
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun DashedDivider(
    color: Color,
    modifier: Modifier = Modifier,
    dashWidth: Float = 10f,
    dashGap: Float = 10f,
    strokeWidth: Float = 3f
) {
    Canvas(modifier = modifier.height(1.dp)) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
        )
    }
}