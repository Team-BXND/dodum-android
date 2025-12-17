package com.example.dodum_android.feature.archive.write

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
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
    viewModel: ArchiveWriteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    // 상태 변수들
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }

    // ★ 중요: 커서 위치와 선택 영역을 제어하기 위해 TextFieldValue 사용
    var content by remember { mutableStateOf(TextFieldValue("")) }

    var selectedCategory by remember { mutableStateOf("동아리") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val categories = listOf("동아리", "나르샤", "대회 수상작", "미니 프로젝트")
    val scrollState = rememberScrollState()

    // 갤러리 실행 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    // ★ 마크다운 포맷팅 헬퍼 함수
    fun insertFormatting(prefix: String, suffix: String) {
        val currentText = content.text
        val selection = content.selection

        val start = selection.start
        val end = selection.end

        // 선택된 텍스트 추출
        val selectedText = currentText.substring(start, end)

        // 앞뒤로 태그 붙이기
        val newText = currentText.replaceRange(start, end, "$prefix$selectedText$suffix")

        // 커서 위치 조정 (텍스트를 감싼 후, 커서를 텍스트 끝 혹은 태그 사이로 이동)
        val newCursorPosition = if (start == end) {
            // 선택된 게 없을 땐 태그 사이에 커서 위치 (**|**)
            start + prefix.length
        } else {
            // 선택된 게 있을 땐 전체 덩어리 뒤로 이동 (**텍스트**|)
            start + prefix.length + selectedText.length + suffix.length
        }

        content = TextFieldValue(
            text = newText,
            selection = TextRange(newCursorPosition)
        )
    }

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 내용 입력 영역 (스크롤 적용)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState) // 스크롤 가능하게 변경
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
                BasicTextField(
                    value = title,
                    onValueChange = { title = it },
                    textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (title.isEmpty()) Text("제목을 입력하세요", fontSize = 24.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 부제목 입력
                BasicTextField(
                    value = subtitle,
                    onValueChange = { subtitle = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Gray),
                    decorationBox = { innerTextField ->
                        if (subtitle.isEmpty()) Text("부제목을 입력하세요 (선택)", fontSize = 18.sp, color = Color.LightGray)
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 팀명 입력
                BasicTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (teamName.isEmpty()) Text("팀 이름을 입력하세요", fontSize = 16.sp, color = Color.LightGray)
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 본문 입력
                // Box를 사용하여 placeholder와 텍스트 필드 겹치기
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp) // 최소 높이 확보
                ) {
                    if (content.text.isEmpty()) {
                        Text("본문을 입력하세요", fontSize = 16.sp, color = Color.LightGray)
                    }
                    BasicTextField(
                        value = content,
                        onValueChange = { content = it }, // TextFieldValue 업데이트
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, lineHeight = 24.sp),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // 선택된 이미지 미리보기
                if (selectedImageUri != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clickable { selectedImageUri = null }, // 클릭 시 삭제
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // 하단 툴바 (키보드 위에 붙어있으면 좋겠지만, 일단 Column 하단에 배치)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) // 배경색 지정 (내용과 구분)
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                HorizontalDivider(color = Color(0xFFE3E3E3), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ★ 마크다운 툴바 아이콘 로직 연결
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        // Bold (**text**)
                        Text(
                            "B",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertFormatting("**", "**") }
                        )
                        // Italic (*text*)
                        Text(
                            "I",
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertFormatting("*", "*") }
                        )
                        // Underline (<u>text</u>) - 마크다운 표준은 아니지만 HTML 태그 사용
                        Text(
                            "U",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertFormatting("<u>", "</u>") }
                        )
                        // Image (Gallery)
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = "Add Image",
                            tint = Color.Gray,
                            modifier = Modifier.clickable { galleryLauncher.launch("image/*") }
                        )
                    }

                    // 버튼 영역
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // 취소
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("취소", fontSize = 14.sp)
                        }

                        // 게시
                        Button(
                            onClick = {
                                viewModel.uploadArchive(
                                    title = title,
                                    subtitle = subtitle,
                                    content = content.text, // ★ 중요: TextFieldValue에서 String만 추출해서 전송
                                    category = selectedCategory,
                                    teamName = teamName,
                                    imageUri = selectedImageUri,
                                    onSuccess = {
                                        Toast.makeText(context, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    },
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text("게시", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}