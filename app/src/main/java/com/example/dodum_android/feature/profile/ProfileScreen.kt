package com.example.dodum_android.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dodum_android.ui.components.TopAppBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    profileId: String
) {
    LaunchedEffect(Unit) { viewModel.loadMockData() }
    val profile = viewModel.profile.value

    Column {
        TopAppBar(navController, profileId)

        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(vertical = 17.dp)
                .height(228.dp)
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 11.dp, top = 11.dp)
                    .width(98.dp)
                    .height(28.dp)
                    .align(Alignment.TopEnd)
                    .background(MainColor, RoundedCornerShape(8.dp))
            ) {
                Text(
                    "나의 정보",
                    fontSize = 17.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column {
                Row(horizontalArrangement = Arrangement.Start) {
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 12.dp)
                            .width(80.dp)
                            .height(80.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )

                    Column(
                        modifier = Modifier
                            .padding(top = 34.dp, start = 20.dp)
                    ) {
                        if (profile != null) {
                            Text(
                                text = profile.username,
                                fontSize = 29.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        }

                        Column(Modifier.padding(top = 8.dp)) {
                            if (profile != null) {
                                Text(
                                    text = profile.email,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            if (profile != null) {
                                Text(
                                    text = "${profile.grade}학년 ${profile.class_no}반 ${profile.student_no}번",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            if (profile != null) {
                                Text(
                                    text = profile.club,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(22.dp, Alignment.CenterHorizontally)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Archive,
                                contentDescription = "내가 쓴 글"
                            )
                            Text(text = "내가 쓴 글", fontSize = 17.sp)
                        }
                        Text("${62}개")
                    }

                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(52.dp)
                            .background(Color.Black)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = "내가 쓴 댓글"
                            )
                            Text(text = "내가 쓴 댓글", fontSize = 17.sp)
                        }
                        Text("${310}개")
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(vertical = 17.dp)
                        .fillMaxSize()
                        .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                        .background(Color.White, RoundedCornerShape(16.dp))
                )
            }
        }
    }
}
