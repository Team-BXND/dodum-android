package com.example.dodum_android.feature.archive.detail

import android.widget.Toast
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ArchiveDetailScreen(
    navController: NavController,
    archiveId: Long,
    viewModel: ArchiveDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(archiveId) {
        viewModel.loadDetail(archiveId)
    }

    val detail by viewModel.detail.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
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
            // 임시 권한 설정 (실제 로직에 맞게 조정 필요)
            val isAuthor = true
            val isAdmin = userRole == "ADMIN"
            val canShowMenu = true

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // 상단 타이틀 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 15.dp, top = 20.dp, bottom = 20.dp)
                ) {
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

                        if (!data.subtitle.isNullOrEmpty()) {
                            Text(
                                text = data.subtitle,
                                fontSize = 14.sp,
                                color = Color(0xFFADADAD),
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Text(
                            text = data.teamname.toString(),
                            fontSize = 14.sp,
                            color = Color(0xFFADADAD),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (canShowMenu) {
                        Box(modifier = Modifier.align(Alignment.TopEnd)) {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                if (isAuthor) {
                                    DropdownMenuItem(
                                        text = { Text("수정") },
                                        onClick = {
                                            showMenu = false
                                            navController.navigate("archiveModify/$archiveId")
                                        }
                                    )
                                }
                                if (isAuthor || isAdmin) {
                                    DropdownMenuItem(
                                        text = { Text("삭제", color = Color.Red) },
                                        onClick = {
                                            showMenu = false
                                            viewModel.deleteArchive(archiveId) {
                                                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                                navController.popBackStack()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                } // End Title Box

                // 구분선과 본문이 Column 안에 포함되어야 합니다.
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

                Spacer(modifier = Modifier.height(50.dp))
            } // End Column (이 괄호의 위치가 문제였습니다)
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