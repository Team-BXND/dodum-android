package com.example.dodum_android.feature.archive

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.example.dodum_android.ui.component.card.ArchiveCard
import com.example.dodum_android.ui.theme.MainColor
import com.example.dodum_android.ui.component.category.ArchiveCategory

@Composable
fun ArchiveScreen(
    navController: NavHostController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    // 뷰모델의 상태를 관찰 (State -> UI 자동 업데이트)
    val archiveList by viewModel.archiveList.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("동아리", "나르샤", "대회 수상작", "미니 프로젝트")

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController, "archive") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavGroup.ArchiveWrite) },
                containerColor = MainColor,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Write Archive")
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            ArchiveCategory(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { newCategory ->
                    viewModel.selectCategory(newCategory)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 아카이브 리스트
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(archiveList) { item ->
                    ArchiveCard(
                        item = item,
                        onClick = {
                            navController.navigate("archiveDetail/${item.id}")
                        }
                    )
                }
            }
        }
    }
}