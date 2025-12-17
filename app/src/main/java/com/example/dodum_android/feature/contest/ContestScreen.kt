package com.example.dodum_android.feature.contest

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
        }
    }
}