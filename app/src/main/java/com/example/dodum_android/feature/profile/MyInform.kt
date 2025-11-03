package com.example.dodum_android.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ChangeProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    profileId: String
) {
    // 실제 통신시 사용
//    LaunchedEffect(Unit) {
//        viewModel.loadProfile(profileId)
//        viewModel.loadPosts()
//    }

    LaunchedEffect(Unit) {
        viewModel.loadMockData()
    }

    val profile = viewModel.profile.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 13.dp)
                    .padding(top = 53.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Image(
                    painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(168.dp)
                        .height(55.33.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painterResource(R.drawable.account_circle),
                    contentDescription = "profile Icon",
                    modifier = Modifier
                        .padding(horizontal = 7.66.dp)
                        .fillMaxHeight()
                )
            }

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
                if (profile != null) {
                    Text(
                        text = profile.username, // 이름
                        fontSize = 33.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 8.dp)
                ) {
                    Column { // 아이디
                        Text(
                            text = "아이디",
                            fontSize = 19.sp
                        )
                        if (profile != null) {
                            Text(
                                text = profile.studentId,
                                fontSize = 29.sp

                            )
                        }
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 학번
                        Text(
                            text = "학번",
                            fontSize = 19.sp
                        )
                        if (profile != null) {
                            Text(
                                text = profile.grade + profile.class_no + profile.student_no,
                                fontSize = 29.sp

                            )
                        }
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 전화번호
                        Text(
                            text = "전화번호",
                            fontSize = 19.sp
                        )
                        if (profile != null) {
                            Text(
                                text = profile.phone,
                                fontSize = 29.sp

                            )
                        }
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 이메일 주소
                        Text(
                            text = "이메일 주소",
                            fontSize = 19.sp
                        )
                        if (profile != null) {
                            Text(
                                text = profile.email,
                                fontSize = 29.sp

                            )
                        }
                    }

                    Column(modifier = Modifier.padding(top = 12.dp)) { // 동아리
                        Text(
                            text = "동아리",
                            fontSize = 19.sp
                        )
                        if (profile != null) {
                            Text(
                                text = profile.club,
                                fontSize = 29.sp

                            )
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()
                    .padding(top = 80.dp)) {
                    Box(modifier = Modifier
                        .padding(start = 69.dp)
                        .width(124.dp)
                        .height(35.dp)
                        .background(MainColor, shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {TODO()})
                    ){
                        Text(
                            text = "내 정보 수정",
                            fontSize = 19.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(modifier = Modifier
                        .padding(end = 72.dp)
                        .width(103.dp)
                        .height(35.dp)
                        .background(Color.Red, shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {TODO()})
                    ){
                        Text(
                            text = "로그아웃",
                            fontSize = 19.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}