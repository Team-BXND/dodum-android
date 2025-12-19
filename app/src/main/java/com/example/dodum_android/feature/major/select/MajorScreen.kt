package com.example.dodum_android.feature.major.select

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.major.Answer
import com.example.dodum_android.ui.component.major.Multiple
import com.example.dodum_android.ui.theme.MainColor
import com.example.dodum_android.ui.theme.SubColor
import kotlinx.coroutines.delay

@Composable
fun MajorScreen(
    navController: NavController,
) {
    val viewModel: MajorViewModel = hiltViewModel()
    val mixedQuestions by viewModel.questions.collectAsState()
    val result by viewModel.result.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var isDone by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController)

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SubColor, fontWeight = FontWeight.Bold)) {
                    append("MBTI ")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("기반 전공 추천 서비스")
                }
            },
            fontSize = 29.sp
        )


        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .size(110.dp),
                    strokeWidth = 8.dp,
                    color = MainColor
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                        .height(20.dp)
                )
                Text(
                    text = "분석 결과를 기다리는 중이에요...",
                    fontSize = 29.sp,
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                )
            }
        } else{
            if(isDone) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ){
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = "check",
                        tint = MainColor,
                        modifier = Modifier
                            .size(171.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                            .height(20.dp)
                    )
                    Text(
                        text = "분석 완료!",
                        fontSize = 29.sp,
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                    )
                }
            }else {

                mixedQuestions?.let { mixed ->

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 25.dp)
                            .padding(top = 23.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // 객관식 질문들
                        items(mixed.objective) { q ->
                            var selectedIndex by remember { mutableStateOf(2) }

                            Multiple(
                                question = q.text,
                                selectedIndex = selectedIndex,
                                onSelect = {
                                    selectedIndex = it
                                    viewModel.selectObjective(q.id, it)
                                }
                            )
                        }

                        // 주관식 질문들
                        items(mixed.subjective) { q ->
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

                        item {
                            Spacer(Modifier.height(30.dp))

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 66.dp)
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(16.dp))
                                    .border(1.dp, MainColor, RoundedCornerShape(16.dp))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        viewModel.requestMajorRecommend()
                                        isLoading = true
                                    }) {
                                Text(
                                    "제출하기",
                                    fontSize = 24.sp,
                                    color = MainColor,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

            } ?: run {
                viewModel.generateAndSave()
            }
        }

        result?.let { data ->
            LaunchedEffect(data) {
                isLoading = false
                isDone = true
                navController.navigate("major_result")
            }
        }
    }
}