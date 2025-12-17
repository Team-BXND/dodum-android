package com.example.dodum_android.feature.start.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.button.AuthButton
import com.example.dodum_android.ui.component.dropdownmenu.AuthDropdown
import com.example.dodum_android.ui.component.textfield.AuthIntField
import com.example.dodum_android.ui.component.textfield.AuthTextField

@Composable
fun SignupInfoScreen(
    navController: NavHostController
) {
    val signupViewModel: SignupViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier.height(78.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.Start)
            ) {
                Spacer(modifier = Modifier.width(35.dp))

                Text(
                    text = "회원가입 정보를\n입력해주세요",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth() ,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val grade = listOf("1학년", "2학년", "3학년")
                val club = listOf("BIND", "3D", "두카미", "Louter", "CNS", "모디", "ALT", "Chatty")
                var selectedGrade by remember { mutableStateOf("") }
                var selectedClub by remember { mutableStateOf("") }

                var classNo by remember { mutableIntStateOf(0) }
                var studentNo by remember { mutableIntStateOf(0) }
                var phone by remember { mutableStateOf("") }

                var isError by remember { mutableStateOf(false) }

//                Text(
//                    text = "학번",
//                    color = Color.Black,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.SemiBold
//                )

                Row {
                    AuthDropdown(
                        fieldname = "학번",
                        placeholder = "학년",
                        selects = grade,
                        selectedClub = selectedGrade,
                        onClubSelected = { selectedGrade = it },
                        width = 110,
                        isError = isError
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    AuthIntField(
                        placename = "반",
                        value = classNo,
                        onValueChange = { classNo = it },
                        iserror = isError
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    AuthIntField(
                        placename = "번호",
                        value = studentNo,
                        onValueChange = { studentNo = it },
                        iserror = isError
                    )
                }

                Spacer(modifier = Modifier .height(12.dp))

                AuthTextField(
                    fieldname = "전화번호",
                    value = phone,
                    onValueChange = { phone = it },
                    iserror = isError,
                    placeholder = "전화번호를 입력해주세요."
                )

                Spacer(modifier = Modifier .height(12.dp))

                AuthDropdown(
                    fieldname = "동아리",
                    placeholder = "동아리",
                    selects = club,
                    selectedClub = selectedClub,
                    onClubSelected = { selectedClub = it },
                    width = 330,
                    isError = isError
                )

                Spacer(modifier = Modifier .height(35.dp))

                AuthButton(
                    buttonName = "다음",
                    onClick = {
                        val hasError = selectedGrade.isEmpty() || phone.isEmpty()
                        isError = hasError

                        if (!hasError) {
                            signupViewModel.form = signupViewModel.form.copy(
                                grade = when (selectedGrade) {
                                    "1학년" -> 1
                                    "2학년" -> 2
                                    "3학년" -> 3
                                    else -> null
                                },
                                classNo = classNo,
                                studentNo = studentNo,
                                phone = phone,
                                club = selectedClub
                            )

                            navController.navigate("signupEmail")
                        }
                    }
                )

            }
        }

    }
}