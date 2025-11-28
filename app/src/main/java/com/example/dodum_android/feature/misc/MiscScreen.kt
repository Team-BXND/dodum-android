package com.example.dodum_android.feature.misc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.feature.info.share.ShareViewModel
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.indicator.PageIndicator
import com.example.dodum_android.ui.component.info.CategoryListItem


@Composable
fun MiscScreen(
    navController: NavController
){
    val viewModel: ShareViewModel = hiltViewModel()

    val list = viewModel.infoList
    val currentPage = viewModel.currentPage
    val totalPages = viewModel.totalPages

    Scaffold(
        topBar = { TopBar(navController) },
        containerColor = Color.White,
        bottomBar = {
            PageIndicator(
                currentPage = currentPage,
                totalPages = totalPages,
                onPageChange = { page ->
                    viewModel.loadPage(page)
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(list) { item ->
                CategoryListItem(item) {
                    navController.navigate(NavGroup.Info)
                }
            }
        }
    }
}