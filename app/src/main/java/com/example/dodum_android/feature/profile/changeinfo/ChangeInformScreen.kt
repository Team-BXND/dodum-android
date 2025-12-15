package com.example.dodum_android.feature.profile.changeinfo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.NavBar
import com.example.dodum_android.ui.component.bar.TopAppBar
import com.example.dodum_android.ui.component.button.AnimatedClickableBox
import com.example.dodum_android.ui.component.dropdownmenu.ClubDropDownMenu
import com.example.dodum_android.ui.component.textfield.CustomTextField
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ChangeInformScreen(
    navController: NavController
) {
    val viewModel: ChangeInfoViewModel = hiltViewModel()

    var grade by remember { mutableStateOf<Int?>(null) }
    var classNo by remember { mutableStateOf<Int?>(null) }
    var studentNo by remember { mutableStateOf<Int?>(null) }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedClub by remember { mutableStateOf("NONE") }

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
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )
                    Image(
                        painterResource(R.drawable.edit),
                        contentDescription = "edit_profile",
                        modifier = Modifier
                            .size(52.dp)
                            .align(Alignment.BottomEnd)
                            .clickable(onClick = { Log.d(TAG, "ChangeInformScreen: 프로필 변경 클릭") })
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 45.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 학번
                        Text(
                            text = "학번", fontSize = 19.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                CustomTextField(
                                    text = grade?.toString() ?: "",
                                    onTextChange = { grade = it.toIntOrNull() },
                                    placeholderText = "학년"
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                CustomTextField(
                                    text = classNo?.toString() ?: "",
                                    onTextChange = { classNo = it.toIntOrNull() },
                                    placeholderText = "반"
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                CustomTextField(
                                    text = studentNo?.toString() ?: "",
                                    onTextChange = { studentNo = it.toIntOrNull() },
                                    placeholderText = "번호"
                                )
                            }

                        }

                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) { // 전화번호
                        Text(
                            text = "전화번호", fontSize = 19.sp
                        )

                        CustomTextField(
                            text = phone, onTextChange = { phone = it }, placeholderText = "전화번호"
                        )

                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 이메일 주소
                        Text(
                            text = "이메일 주소", fontSize = 19.sp
                        )
                        CustomTextField(
                            text = email, onTextChange = { email = it }, placeholderText = "이메일 주소"
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // 동아리
                        Text(
                            text = "동아리", fontSize = 19.sp
                        )
                        ClubDropDownMenu(
                            selectedClub = selectedClub, onClubSelected = { selectedClub = it })
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        val totalWeight = 128f + 102f + 72f
                        val firstWeight = 128f / totalWeight
                        val secondWeight = 102f / totalWeight
                        val thirdWeight = 72f / totalWeight

                        AnimatedClickableBox (
                            onClick = { navController.navigate("changepw") },
                            modifier = Modifier
                                .weight(firstWeight)
                                .height(43.dp)
                                .background(MainColor, shape = RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "비밀번호 변경",
                                fontSize = 19.sp,
                                color = Color.White,
                            )
                        }

                        AnimatedClickableBox(
                            onClick = {
                                viewModel.updateProfile(
                                    grade = grade ?: 0,
                                    classNo = classNo ?: 0,
                                    studentNo = studentNo ?: 0,
                                    phone = phone,
                                    email = email,
                                    club = selectedClub
                                )
                            },
                            modifier = Modifier
                                .weight(secondWeight)
                                .height(43.dp)
                                .background(MainColor, shape = RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "수정 완료",
                                fontSize = 19.sp,
                                color = Color.White,
                            )
                        }


                        AnimatedClickableBox (
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .weight(thirdWeight)
                                .height(43.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "취소",
                                fontSize = 19.sp,
                                color = Color.White,
                            )
                        }
                    }

                }

            }
            //navController.navigate(NavGroup.Signin)

            NavBar(navController, "ChangePw")

        }

    }
}