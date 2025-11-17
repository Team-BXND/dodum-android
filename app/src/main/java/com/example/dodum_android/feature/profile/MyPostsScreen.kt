package com.example.dodum_android.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.components.MyPostItem
import com.example.dodum_android.ui.components.TopAppBar

@Composable
fun MypostsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    profileId: Int
) {
    LaunchedEffect(Unit) { viewModel.loadMockData() }
    val posts by viewModel.myPosts.collectAsState()

    Column {
        TopAppBar(navController, profileId)
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 17.dp)
                .fillMaxSize()
                .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                .background(Color.White, RoundedCornerShape(16.dp, 16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = "내가 쓴 글",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 28.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(posts) { post ->
                        MyPostItem(post)
                    }
                }
            }
        }
    }

}