package com.example.dodum_android.feature.start.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.button.AuthButton
import com.example.dodum_android.ui.component.textfield.AuthTextField

@Composable
fun SignupIdPwScreen(
    navController: NavHostController
) {
    val signupViewModel: SignupViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier .height(78.dp))

            Row (modifier = Modifier
                .align(Alignment.Start)
            ){
                Spacer(modifier = Modifier .width(35.dp))

                Text(text = "회원가입 정보를\n입력해주세요",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier .height(20.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordcheck by remember { mutableStateOf("") }

            var isError by remember { mutableStateOf(false) }

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AuthTextField(
                    fieldname = "아이디",
                    value = username,
                    onValueChange = { username = it },
                    iserror = isError,
                    placeholder = "아이디를 입력해주세요."
                )

                Spacer(modifier = Modifier .height(12.dp))

                AuthTextField(
                    fieldname = "비밀번호",
                    value = password,
                    onValueChange = { password = it },
                    iserror = isError,
                    placeholder = "비밀번호를 입력해주세요."
                )

                Spacer(modifier = Modifier .height(12.dp))

                AuthTextField(
                    fieldname = "비밀번호 확인",
                    value = passwordcheck,
                    onValueChange = { passwordcheck = it },
                    iserror = isError,
                    errortext = "입력한 값들을 확인해주세요.",
                    placeholder = "비밀번호를 다시 한 번 입력해주세요."
                )

                Spacer(modifier = Modifier .height(35.dp))

                AuthButton(
                    buttonName = "다음",
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty() && passwordcheck.isNotEmpty() ) {
                            if (password == passwordcheck) {
//                                signupViewModel.updateIdPw(username, password)
                                signupViewModel.form = signupViewModel.form.copy(
                                    username = username,
                                    password = password
                                )
//                                if (signupViewModel.signupSuccess == true) {
//                                    navController.navigate("signupInfo")
//                                }
                                navController.navigate("signupInfo")
                            }
                        } else {
                            isError = true
                        }
                    }
                )

            }

        }

    }
}