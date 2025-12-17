package com.example.dodum_android.feature.contest.write

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.feature.contest.ContestViewModel
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ContestWriteScreen(
    navController: NavController,
    viewModel: ContestViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // 입력 상태
    var title by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 입력 폼 (스크롤 가능)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 25.dp, vertical = 20.dp)
                    .verticalScroll(scrollState)
            ) {
                // 제목
                CustomInput(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "제목을 입력하세요",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textColor = Color.Black
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 정보 필드들
                CustomInput(value = email, onValueChange = { email = it }, placeholder = "이메일을 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = phone, onValueChange = { phone = it }, placeholder = "전화번호를 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = time, onValueChange = { time = it }, placeholder = "일시를 입력하세요")
                Spacer(modifier = Modifier.height(12.dp))
                CustomInput(value = place, onValueChange = { place = it }, placeholder = "장소를 입력하세요")

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE3E3E3))

                // 본문
                Box(modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp)) {
                    if (content.isEmpty()) {
                        Text("본문을 입력하세요", fontSize = 16.sp, color = Color.LightGray)
                    }
                    BasicTextField(
                        value = content,
                        onValueChange = { content = it },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, lineHeight = 24.sp),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // 하단 툴바 및 버튼
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                // 마크다운 툴바 (UI Mockup)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("B", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Gray)
                    Text("I", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 20.sp, color = Color.Gray)
                    Text("U", style = TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline), fontSize = 20.sp, color = Color.Gray)
                    Icon(Icons.Outlined.Image, contentDescription = "Image", tint = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            viewModel.createContest(title, email, phone, time, place, content,
                                onSuccess = {
                                    Toast.makeText(context, "등록되었습니다.", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                },
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(100.dp).height(40.dp)
                    ) {
                        Text("게시", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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