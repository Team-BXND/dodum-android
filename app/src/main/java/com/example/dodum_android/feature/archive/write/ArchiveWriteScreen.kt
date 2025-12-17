package com.example.dodum_android.feature.archive.write

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ArchiveWriteScreen(
    navController: NavController,
    viewModel: ArchiveWriteViewModel = hiltViewModel(),
    archiveId: Long? = null
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val editState by viewModel.editUiState.collectAsState()

    // 상태 변수들
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("동아리") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 수정 모드일 때 초기 데이터 로드 및 적용
    LaunchedEffect(archiveId) {
        if (archiveId != null) {
            viewModel.loadArchiveForEdit(archiveId)
        }
    }

    // editState가 로드되면 UI 업데이트
    LaunchedEffect(editState) {
        editState?.let {
            title = it.title
            subtitle = it.subtitle
            content = it.content
            teamName = it.teamName
            selectedCategory = it.category
            // 이미지는 URL을 Uri로 변환하거나 별도 처리 필요 (여기선 생략)
        }
    }

    val categories = listOf("동아리", "나르샤", "대회 수상작", "미니 프로젝트")
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // ... (입력 영역 UI - 기존과 동일) ...
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 25.dp, vertical = 20.dp)
            ) {
                // 카테고리 선택
                Text("카테고리", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    categories.forEach { category ->
                        val isSelected = selectedCategory == category
                        Text(
                            text = category,
                            fontSize = 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MainColor else Color.Gray,
                            modifier = Modifier.clickable { selectedCategory = category }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 제목 입력
                Box {
                    if (title.isEmpty()) {
                        Text("제목을 입력하세요", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 부제목 입력
                Box {
                    if (subtitle.isEmpty()) {
                        Text("부제목을 입력하세요 (선택)", fontSize = 18.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Gray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 팀명 입력
                Box {
                    if (teamName.isEmpty()) {
                        Text("팀 이름을 입력하세요", fontSize = 16.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = teamName,
                        onValueChange = { teamName = it },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 본문 입력
                Box(modifier = Modifier.weight(1f)) {
                    if (content.isEmpty()) {
                        Text("본문을 입력하세요", fontSize = 16.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = content,
                        onValueChange = { content = it },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, lineHeight = 24.sp),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // 이미지 미리보기
                if (selectedImageUri != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable { selectedImageUri = null },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // 하단 툴바 및 버튼
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                // ... (툴바 아이콘 유지) ...
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        Text("B", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Gray)
                        Text("I", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 20.sp, color = Color.Gray)
                        Text("U", style = TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline), fontSize = 20.sp, color = Color.Gray)
                        Icon(Icons.Outlined.Image, contentDescription = "Add Image", tint = Color.Gray, modifier = Modifier.clickable { galleryLauncher.launch("image/*") })
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) { Text("취소", fontSize = 14.sp) }

                        Button(
                            onClick = {
                                viewModel.submitArchive(
                                    archiveId, title, subtitle, content, selectedCategory, teamName, selectedImageUri,
                                    onSuccess = {
                                        val msg = if (archiveId == null) "게시 완료" else "수정 완료"
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    },
                                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                )
                            },
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(if (archiveId == null) "게시" else "수정", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}