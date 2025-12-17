package com.example.dodum_android.feature.contest

import com.example.dodum_android.feature.contest.ContestViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.network.contest.ContestData
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.NavBar
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.card.ContestCard
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ContestScreen(
    navController: NavHostController,
    viewModel: ContestViewModel = hiltViewModel()
) {
    val contestList by viewModel.contestList.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    // 글쓰기 권한: Senior, Graduate, Admin, Teacher 등
    val canWrite = userRole == "SENIOR" || userRole == "GRADUATE" || userRole == "ADMIN" || userRole == "TEACHER"

    val dummyContestList = listOf(
        ContestData(
            id = 1,
            title = "2025 전국 고등학생 해커톤",
            content = "전국 고등학생을 대상으로 하는 해커톤 대회입니다. 팀 단위 참가이며 소프트웨어, AI, 모바일 앱 등 자유 주제로 진행됩니다.",
            email = "hackathon@contest.kr",
            phone = "02-1234-5678",
            time = "2025.01.10 ~ 2025.01.12",
            place = "서울 코엑스",
            image = "", // 지금 ContestCard에서 image 안 쓰고 있음
            isAlertActive = false
        ),
        ContestData(
            id = 2,
            title = "AI 창의 아이디어 공모전",
            content = "AI 기술을 활용한 창의적인 아이디어를 공모합니다. 개인 및 팀 참가 가능하며 우수작은 시상합니다.",
            email = "ai@idea.or.kr",
            phone = "02-9876-5432",
            time = "2025.02.01 ~ 2025.02.28",
            place = "온라인 접수",
            image = "",
            isAlertActive = true
        ),
        ContestData(
            id = 3,
            title = "청소년 앱 개발 경진대회",
            content = "청소년 대상 모바일 앱 개발 대회입니다. 실사용 가능한 앱을 기준으로 평가합니다.",
            email = "app@devcontest.com",
            phone = "031-222-3333",
            time = "2025.03.15",
            place = "판교 스타트업 캠퍼스",
            image = "",
            isAlertActive = false
        )
    )

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController, "contest") },
        floatingActionButton = {
            if (canWrite) {
                FloatingActionButton(
                    onClick = { navController.navigate(NavGroup.ContestWrite) },
                    containerColor = MainColor,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Write Contest")
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(contestList) { item ->
                ContestCard(item = item) {
                    navController.navigate("contestDetail/${item.id}")
                }
            }

            items(dummyContestList) { item ->
                ContestCard(item = item) {
                    navController.navigate("contestDetail/${item.id}")
                }
            }
        }
    }
}