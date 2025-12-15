package com.example.dodum_android.feature.profile.myinfo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.button.AnimatedClickableBox
import com.example.dodum_android.ui.component.bar.TopAppBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun MyInformScreen(
    navController: NavController
) {
    val profileId: Int = 3

    val viewModel: MyInfoViewModel = hiltViewModel()

    LaunchedEffect(Unit) { viewModel.loadProfile() }
    val profile = viewModel.profile.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBar(navController)


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp),
            ) {

                Box( // 프로필
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 8.dp)
                ) {
                    Column { // 아이디
                        Text(
                            text = "아이디", fontSize = 19.sp
                        )
                            Text(
                                text = profile?.username ?: "", fontSize = 29.sp

                            )
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 학번
                        Text(
                            text = "학번", fontSize = 19.sp
                        )
                            Text(
                                text = "${ profile?.grade ?: 0 }${ profile?.class_no ?: 0 }${ profile?.student_no ?: 0}",
                                fontSize = 29.sp

                            )
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 전화번호
                        Text(
                            text = "전화번호", fontSize = 19.sp
                        )
                            Text(
                                text = profile?.phone ?: "", fontSize = 29.sp

                            )
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 이메일 주소
                        Text(
                            text = "이메일 주소", fontSize = 19.sp
                        )
                            Text(
                                text = profile?.email ?: "", fontSize = 29.sp

                            )
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 동아리
                        Text(
                            text = "동아리", fontSize = 19.sp
                        )
                            Text(
                                text = profile?.club ?: "", fontSize = 29.sp

                            )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    val totalWeight = 124f + 103f
                    val firstWeight = 124f / totalWeight
                    val secondWeight = 103f / totalWeight

                    AnimatedClickableBox(
                        onClick = { navController.navigate(NavGroup.ChangeInfo) },
                        modifier = Modifier
                            .padding(start = 69.dp)
                            .weight(firstWeight)
                            .height(35.dp)
                            .background(MainColor, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "내 정보 수정",
                            fontSize = 19.sp,
                            color = Color.White,
                        )
                    }

                    AnimatedClickableBox (
                        onClick = { viewModel.SignOut { success, message ->
                            if (success) {
                                Log.d(TAG, "로그아웃 성공: $message")
                                navController.navigate(NavGroup.Signin) {
                                    popUpTo(0)
                                }
                            } else {
                                Log.d(TAG, "로그아웃 실패: $message")
                            }
                        } },
                        modifier = Modifier
                            .padding(end = 72.dp)
                            .weight(secondWeight)
                            .height(35.dp)
                            .background(Color.Red, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "로그아웃",
                            fontSize = 19.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}