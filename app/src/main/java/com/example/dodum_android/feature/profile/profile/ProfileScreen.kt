package com.example.dodum_android.feature.profile.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopAppBar
import com.example.dodum_android.ui.component.button.AnimatedButton
import com.example.dodum_android.ui.component.profile.MyPostItem
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
        viewModel.loadMyPosts()
    }

    val profile by viewModel.profile
    val posts by viewModel.myPosts.collectAsState()
    val myPostCount by viewModel.myPostCount
    val userRole by viewModel.userRole

    Column {
        TopAppBar(navController)

        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 17.dp)
                .height(228.dp)
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                AnimatedButton(
                    onClick = { navController.navigate(NavGroup.MyInfo) },
                    modifier = Modifier
                        .padding(end = 11.dp, top = 11.dp)
                        .width(98.dp)
                        .height(28.dp)
                        .background(MainColor, RoundedCornerShape(8.dp))
                        .align(Alignment.End)
                ) {
                    Text("나의 정보", fontSize = 17.sp, color = Color.White)
                }

                if (userRole in listOf("TEACHER", "ADMIN")) {
                    AnimatedButton(
                        onClick = { navController.navigate(NavGroup.FalsePost) },
                        modifier = Modifier
                            .padding(end = 11.dp, top = 11.dp)
                            .width(98.dp)
                            .height(28.dp)
                            .background(MainColor, RoundedCornerShape(8.dp))
                            .align(Alignment.End)
                    ) {
                        Text("미승인 글", fontSize = 17.sp, color = Color.White)
                    }
                }
            }

            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 12.dp)
                            .size(80.dp)
                            .background(Color.Gray, CircleShape)
                    )

                    Column(modifier = Modifier.padding(top = 34.dp, start = 20.dp)) {
                        Text(profile?.username ?: "", fontSize = 29.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(profile?.email ?: "")
                        Text("${profile?.grade ?: 0}학년 ${profile?.class_no ?: 0}반 ${profile?.student_no ?: 0}번")
                        Text(profile?.club ?: "")
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Archive, contentDescription = null)
                            Spacer(Modifier.width(5.dp))
                            Text("내가 쓴 글", fontSize = 17.sp)
                        }
                        Text("${myPostCount}개")
                    }
                }
            }
        }

        AnimatedButton(
            onClick = { navController.navigate(NavGroup.MyPosts) }
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 17.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("내가 쓴 글", fontSize = 24.sp)
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                    }

                    Spacer(Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(posts.take(2)) { post ->
                            MyPostItem(post)
                        }
                    }
                }
            }
        }
    }
}