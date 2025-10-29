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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.dropdownmenu.AuthDropdown
import com.example.dodum_android.ui.component.textfield.AuthIntField

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

                var class_no by remember { mutableStateOf("") }
                var student_no by remember { mutableStateOf("") }
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
                        value = class_no,
                        onValueChange = { class_no = it },
                        iserror = isError
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    AuthIntField(
                        placename = "번호",
                        value = student_no,
                        onValueChange = { student_no = it },
                        iserror = isError
                    )

                }



            }
        }

    }
}