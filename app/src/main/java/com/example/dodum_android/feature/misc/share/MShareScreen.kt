package com.example.dodum_android.feature.misc.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.indicator.PageIndicator
import com.example.dodum_android.ui.component.info.CategoryListItem
import com.example.dodum_android.ui.theme.MainColor

/* ---------- Category Tab ---------- */

@Composable
private fun CategoryTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .width(IntrinsicSize.Max)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = if (isSelected) MainColor else Color.Gray,
            maxLines = 1,
            softWrap = false
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MainColor)
            )
        }
    }
}

/* ---------- Screen ---------- */

@Composable
fun MShareScreen(
    navController: NavController
) {
    val viewModel: MShareViewModel = hiltViewModel()
    val list by viewModel.miscList.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()

    var selectedCategory by remember { mutableStateOf("") }

    val categories = listOf(
        "lecture" to "강의정보",
        "tool" to "개발도구",
        "platform" to "플랫폼추천",
        "school_support" to "학교지원"
    )

    Scaffold(
        topBar = { TopBar(navController) },
        floatingActionButton = {
            Image(
                painter = painterResource(R.drawable.add),
                contentDescription = "글 쓰기",
                modifier = Modifier
                    .size(52.dp)
                    .clickable(indication = null,
                        interactionSource = null) {
                        navController.navigate(NavGroup.MWRITE)
                    }
            )
        },
        containerColor = Color.White,
        bottomBar = {
            PageIndicator(
                currentPage = currentPage,
                totalPages = totalPages,
                onPageChange = viewModel::loadPage,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            /* ---------- Category LazyRow ---------- */

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 13.dp, vertical = 10.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(categories) { (code, label) ->
                        CategoryTab(
                            text = label,
                            isSelected = selectedCategory == code,
                            onClick = {
                                selectedCategory =
                                    if (selectedCategory == code) "" else code
                                viewModel.loadPage(1)
                            }
                        )
                    }
                }
            }

            /* ---------- List ---------- */

            val filteredList = if (selectedCategory.isBlank()) {
                list
            } else {
                list.filter { it.category == selectedCategory }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredList) { item ->
                    CategoryListItem(item) {
                        navController.navigate(NavGroup.MISC)
                    }
                }
            }
        }
    }
}
