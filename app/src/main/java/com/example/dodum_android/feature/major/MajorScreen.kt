package com.example.dodum_android.feature.major

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.major.Answer
import com.example.dodum_android.ui.component.major.Multiple
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun MajorScreen(
    navController: NavController,
    viewModel: MajorViewModel = hiltViewModel(),
) {

    val mixedQuestions by viewModel.questions.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .padding(top = 23.dp)
        ) {
            Text(
                text = "테스트 기반 전공 추천 서비스",
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            mixedQuestions?.let { mixed ->

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    // 🔵 주관식 질문 2개
                    mixed.subjective.forEach { q ->
                        var text by remember { mutableStateOf("") }

                        Answer(
                            question = q.text,
                            text = text,
                            onTextChange = {
                                text = it
                                viewModel.inputSubjective(q.key, it)
                            }
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    // 🔴 객관식 질문 18개
                    mixed.objective.forEach { q ->
                        var selectedIndex by remember { mutableStateOf(1) }

                        Multiple(
                            question = q.text,
                            selectedIndex = selectedIndex,
                            onSelect = {
                                selectedIndex = it
                                viewModel.selectObjective(q.id, it)
                            }
                        )
                    }

                    Spacer(Modifier.height(30.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(43.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, MainColor, RoundedCornerShape(16.dp))
                    ){

                    }
                }
            } ?: run {

                // 첫 진입 시 로딩 또는 버튼
                Button(onClick = { viewModel.generateAndSave() }) {
                    Text("문항 생성하기")
                }
            }
        }
    }
}
