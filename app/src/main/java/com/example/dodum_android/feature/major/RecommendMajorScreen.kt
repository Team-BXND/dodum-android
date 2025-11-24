package com.example.dodum_android.feature.major

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopAppBar
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.major.Answer
import com.example.dodum_android.ui.component.major.Multiple

@Composable
fun RecommendMajorScreen(
    navController: NavController,

){
    var first by remember { mutableStateOf("") }
    var second by remember { mutableStateOf("") }
    var third by remember { mutableStateOf(2) }
    var fourth by remember { mutableStateOf(2) }



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController = navController)

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Answer(
                    "30. 질문ㅇㄴㅁㅇㄴㅁㅇㄴㅁ",
                    first,
                    onTextChange = {first = it}
                )

                Answer(
                    "30. 질문ㅇㄴㅁㅇㄴㅁㅇㄴㅁ",
                    second,
                    onTextChange = {second = it}
                )


                Multiple (
                    "30. 질문ㅇㄴㅁㅇㄴㅁㅇㄴㅁ",
                    selectedIndex = third,
                    onSelect = { third = it }
                )


                Multiple (
                    "30. 질문ㅇㄴㅁㅇㄴㅁㅇㄴㅁ",
                    selectedIndex = fourth,
                    onSelect = { fourth = it }
                )
            }

        }
    }
}