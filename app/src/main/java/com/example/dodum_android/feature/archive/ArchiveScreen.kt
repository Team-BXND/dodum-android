package com.example.dodum_android.feature.archive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dodum_android.network.archive.ArchiveItem
import com.example.dodum_android.ui.component.bar.NavBar
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.card.ArchiveCard

@Composable
fun ArchiveScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController, "archive") },
        containerColor = Color.White
    ) { innerPadding ->

        val exampleArchiveItem = ArchiveItem(
            id = 1L,
            title = "학교 주변 안전 취약 구역 분석",
            teamname = "Dodum",
            category = "사회 안전",
            description = "이것은 예시 아카이브입니다."
        )

        Column (modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 25.dp, vertical = 20.dp)

        ) {
            ArchiveCard(
                item = exampleArchiveItem, {}
            )
        }
    }
}