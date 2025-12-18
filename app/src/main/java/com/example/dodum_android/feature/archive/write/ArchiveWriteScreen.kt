package com.example.dodum_android.feature.archive.write

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.util.MarkdownVisualTransformation
import com.example.dodum_android.ui.theme.MainColor // DodumMainColor로 변경 권장
import kotlinx.coroutines.flow.collectLatest // Flow 이벤트 처리를 위해 임포트

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveWriteScreen(
    navController: NavController,
    viewModel: ArchiveWriteViewModel = hiltViewModel(),
    archiveId: Long? = null
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    // ViewModel의 상태를 관찰하여 UI 업데이트
    val title by viewModel.title.collectAsState()
    val subtitle by viewModel.subtitle.collectAsState()
    val teamName by viewModel.teamName.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()

    // 버튼 상태
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }
    var isUnderline by remember { mutableStateOf(false) }

    val content by viewModel.content.collectAsState()

    // [★추가 1] 화면 진입 시, 수정 모드(archiveId 존재)라면 데이터 로드 요청
    LaunchedEffect(archiveId) {
        if (archiveId != null && archiveId != 0L) {
            viewModel.loadArchiveForEdit(archiveId)
        }
    }

    val markdownVisualTransformation = remember { MarkdownVisualTransformation() }

    // [중요] contentTextFieldValue를 위한 상태
    var contentTextFieldValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    // [★추가 2] ViewModel에서 데이터를 불러왔을 때, 로컬 TextFieldValue에 반영
    LaunchedEffect(content) {
        // 서버에서 불러온 값이 있고, 현재 입력창이 비어있거나 다를 때만 업데이트 (커서 튐 방지)
        if (content.isNotEmpty() && contentTextFieldValue.text != content) {
            contentTextFieldValue = contentTextFieldValue.copy(text = content)
        }
    }


    // 수정 모드 데이터 로드 완료 시 본문 필드 업데이트
    LaunchedEffect(content) {
        // 커서 위치를 유지하면서 텍스트만 업데이트
        if (content != contentTextFieldValue.text) {
            contentTextFieldValue = contentTextFieldValue.copy(text = content)
        }
    }

    // ViewModel의 일회성 이벤트를 처리
    LaunchedEffect(Unit) { // 한 번만 실행되도록 Unit 키 사용
        viewModel.event.collectLatest { event ->
            when (event) {
                is ArchiveWriteEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                ArchiveWriteEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val categories = listOf("동아리", "나르샤", "대회 수상작", "미니 프로젝트")
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> viewModel.onImageSelect(uri) } // ViewModel로 URI 전달

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
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
                            color = if (isSelected) MainColor else Color.Gray, // MainColor -> DodumMainColor 권장
                            modifier = Modifier.clickable { viewModel.onCategorySelect(category) } // ViewModel로 전달
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // [수정] 제목 입력 (Box 조건문 수정: 값이 있어도 Placeholder처럼 보이는 문제 방지)
                Box {
                    if (title.isEmpty()) {
                        Text("제목을 입력하세요", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // [수정] 부제목 입력
                Box {
                    if (subtitle.isEmpty()) {
                        Text("부제목을 입력하세요 (선택)", fontSize = 18.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = subtitle,
                        onValueChange = { viewModel.onSubtitleChange(it) },
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Gray),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // [수정] 팀명 입력
                Box {
                    if (teamName.isEmpty()) {
                        Text("팀 이름을 입력하세요", fontSize = 16.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = teamName,
                        onValueChange = { viewModel.onTeamNameChange(it) },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 본문 입력
                Box(modifier = Modifier.weight(1f)) {
                    if (contentTextFieldValue.text.isEmpty()) {
                        Text("본문을 입력하세요", fontSize = 16.sp, color = Color(0xFFADADAD))
                    }
                    BasicTextField(
                        value = contentTextFieldValue,
                        onValueChange = {
                            contentTextFieldValue = it
                            viewModel.onContentChange(it.text)
                        },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, lineHeight = 24.sp),
                        // ★ [핵심] 여기서 VisualTransformation 적용
                        visualTransformation = markdownVisualTransformation,
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
                            .clickable { viewModel.onImageRemove() }, // ViewModel로 이미지 제거 요청
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
                // insertMarkdown 함수 정의
                fun insertMarkdown(prefix: String, suffix: String) {
                    val text = contentTextFieldValue.text
                    val selection = contentTextFieldValue.selection
                    val start = selection.start.coerceAtLeast(0).coerceAtMost(text.length)
                    val end = selection.end.coerceAtLeast(0).coerceAtMost(text.length)
                    val before = text.substring(0, start)
                    val selected = text.substring(start, end)
                    val after = text.substring(end)
                    val newText = before + prefix + selected + suffix + after
                    val newCursorPos = start + prefix.length + selected.length + suffix.length
                    contentTextFieldValue = TextFieldValue(
                        text = newText,
                        selection = androidx.compose.ui.text.TextRange(newCursorPos, newCursorPos)
                    )
                    viewModel.onContentChange(newText)
                }

                // 버튼 클릭 시 마크다운 삽입 + 상태 토글
                fun toggleMarkdown(prefix: String, suffix: String, state: MutableState<Boolean>) {
                    insertMarkdown(prefix, suffix)
                    state.value = !state.value
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        // Bold 버튼
                        Text(
                            text = "B",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = if(isBold) MainColor else Color.Gray,
                            modifier = Modifier.clickable { toggleMarkdown("**", "**", mutableStateOf(isBold).also { isBold = !isBold }) }
                        )
                        // Italic 버튼
                        Text(
                            text = "I",
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            fontSize = 20.sp,
                            color = if(isItalic) MainColor else Color.Gray,
                            modifier = Modifier.clickable { toggleMarkdown("*", "*", mutableStateOf(isItalic).also { isItalic = !isItalic }) }
                        )
                        // Underline 버튼
                        Text(
                            text = "U",
                            style = TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline),
                            fontSize = 20.sp,
                            color = if(isUnderline) MainColor else Color.Gray,
                            modifier = Modifier.clickable { toggleMarkdown("<u>", "</u>", mutableStateOf(isUnderline).also { isUnderline = !isUnderline }) }
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { navController.popBackStack() }, // "취소" 버튼은 그냥 뒤로 가기
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) { Text("취소", fontSize = 14.sp) }

                        Button(
                            onClick = { viewModel.submitArchive(archiveId) }, // ViewModel로 submit 요청
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MainColor), // MainColor -> DodumMainColor 권장
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(if (archiveId == null) "게시" else "수정", fontSize = 14.sp)
                        }
                    }
                }

            }
        }
        // 로딩 스피너
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MainColor) // MainColor -> DodumMainColor 권장
            }
        }
    }
}