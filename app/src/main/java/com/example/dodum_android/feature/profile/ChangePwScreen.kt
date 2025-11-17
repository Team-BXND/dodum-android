package com.example.dodum_android.feature.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dodum_android.ui.components.CustomTextField
import com.example.dodum_android.ui.components.TopAppBar
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ChangePwScreen(
    navController: NavController,
    profileId: Int
){

    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(navController, profileId)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp),
            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 45.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 아이디
                        Text(
                            text = "기존 비밀번호", fontSize = 19.sp
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomTextField(
                                text = password,
                                onTextChange = { password = it },
                                placeholderText = "기존 비밀번호"
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 아이디
                        Text(
                            text = "새 비밀번호", fontSize = 19.sp
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomTextField(
                                text = newPassword,
                                onTextChange = { newPassword = it },
                                placeholderText = "새 비밀번호"
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 아이디
                        Text(
                            text = "새 비밀번호 확인", fontSize = 19.sp
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomTextField(
                                text = passwordCheck,
                                onTextChange = { passwordCheck = it },
                                placeholderText = "새 비밀번호 확인"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 167.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        val totalWeight = 124f + 103f
                        val firstWeight = 124f / totalWeight
                        val secondWeight = 103f / totalWeight

                        Box(
                            modifier = Modifier
                                .padding(start = 69.dp)
                                .weight(firstWeight)
                                .height(35.dp)
                                .background(MainColor, shape = RoundedCornerShape(8.dp))
                                .clickable(onClick = { Log.d(TAG, "ChangePwScreen: 비밀번호 변경 클릭됨") })
                        ) {
                            Text(
                                text = "비밀번호 변경",
                                fontSize = 19.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(end = 72.dp)
                                .weight(secondWeight)
                                .height(35.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                                .clickable(onClick = { navController.popBackStack() })
                        ) {
                            Text(
                                text = "취소",
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
}