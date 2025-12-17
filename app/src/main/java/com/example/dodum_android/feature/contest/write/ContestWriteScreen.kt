package com.example.dodum_android.feature.contest.write

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.dodum_android.feature.contest.ContestViewModel
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ContestWriteScreen(
    navController: NavController,
    viewModel: ContestViewModel = hiltViewModel(),
    contestId: Int? = null // 수정 시 ID 받음
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isLoading by viewModel.isLoading.collectAsState()
    val editState by viewModel.editUiState.collectAsState()

    // 입력 상태
    var title by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var placeInput by remember { mutableStateOf("") }

    // [수정] content를 TextFieldValue로 관리하여 커서/선택영역 제어
    var content by remember { mutableStateOf(TextFieldValue("")) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 수정 모드 초기화
    LaunchedEffect(contestId) {
        if (contestId != null) {
            viewModel.loadContestForEdit(contestId)
        }
    }

    // 데이터 로드 완료 시 UI 반영
    LaunchedEffect(editState) {
        editState?.let { state ->
            title = state.title
            // subtitle은 없으므로 생략
            email = state.email
            phone = state.phone
            time = state.time
            placeInput = state.place
            content = TextFieldValue(state.subTitle.toString()) // 초기값 설정
            // 이미지는 URL 처리 필요 (여기선 생략)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    // 마크다운 삽입 함수
    fun insertMarkdown(prefix: String, suffix: String) {
        val currentText = content.text
        val selection = content.selection

        val beforeSelection = currentText.substring(0, selection.start)
        val selectedText = currentText.substring(selection.start, selection.end)
        val afterSelection = currentText.substring(selection.end)

        val newText = "$beforeSelection$prefix$selectedText$suffix$afterSelection"

        // 커서 위치 조정: 선택된 텍스트가 있으면 감싼 뒤로, 없으면 태그 사이로
        val newCursorPos = if (selectedText.isNotEmpty()) {
            selection.end + prefix.length + suffix.length
        } else {
            selection.start + prefix.length
        }

        content = TextFieldValue(
            text = newText,
            selection = TextRange(newCursorPos)
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
            // 입력 폼
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 25.dp, vertical = 20.dp)
                    .verticalScroll(scrollState)
            ) {
                CustomInput(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "제목을 입력하세요",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textColor = Color.Black
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                CustomInput(value = email, onValueChange = { email = it }, placeholder = "이메일을 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = phone, onValueChange = { phone = it }, placeholder = "전화번호를 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = time, onValueChange = { time = it }, placeholder = "일시를 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = placeInput, onValueChange = { placeInput = it }, placeholder = "장소를 입력하세요")

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                Box(modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp)) {
                    if (content.text.isEmpty()) {
                        Text("본문을 입력하세요", fontSize = 16.sp, color = Color.LightGray)
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

            // 하단 툴바
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
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
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertMarkdown("**", "**") }
                        )
                        // Italic 버튼
                        Text(
                            text = "I",
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertMarkdown("*", "*") }
                        )
                        // Underline 버튼
                        Text(
                            text = "U",
                            style = TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline),
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { insertMarkdown("<u>", "</u>") }
                        )

                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = "Image",
                            tint = Color.Gray,
                            modifier = Modifier.clickable { galleryLauncher.launch("image/*") }
                        )
                    }

                    Row(horizontalArrangement = Arrangement.End) {
                        Button(
                            onClick = {
                                viewModel.submitContest(
                                    contestId, title, email, phone, time, placeInput, content.text, // content.text로 String 전달
                                    onSuccess = {
                                        val msg = if (contestId == null) "등록되었습니다." else "수정되었습니다."
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    },
                                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                )
                            },
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.width(100.dp).height(40.dp)
                        ) {
                            Text(if(contestId == null) "게시" else "수정", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.width(100.dp).height(40.dp)
                        ) {
                            Text("취소", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    fontSize: androidx.compose.ui.unit.TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Gray
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (value.isEmpty()) {
            Text(placeholder, fontSize = fontSize, fontWeight = fontWeight, color = Color.LightGray)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = fontSize, fontWeight = fontWeight, color = textColor),
            modifier = Modifier.fillMaxWidth()
        )
    }
}