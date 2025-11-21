package com.example.dodum_android.feature.start.signin

import androidx.compose.foundation.clickable
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
import com.example.dodum_android.ui.theme.FontGray

@Composable
fun SigninScreen (
    navController: NavHostController
) {
    val signinViewModel: SigninViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize() ) {

        Column {

            Spacer(modifier = Modifier .height(78.dp))

            Row (modifier = Modifier
                .align(Alignment.Start)
            ){
                Spacer(modifier = Modifier .width(35.dp))

                Text(text = "로그인 정보를\n입력해주세요",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier .height(20.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var isError by remember { mutableStateOf(false) }

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AuthTextField(
                    fieldname = "아이디",
                    value =  username,
                    onValueChange = { username = it },
                    iserror =  isError,
                    placeholder = "아이디를 입력해주세요."
//                    errortext = "아이디 또는 비밀번호가 일치하지 않습니다."
                )

                Spacer(modifier = Modifier .height(12.dp))

                AuthTextField(
                    fieldname = "비밀번호",
                    value =  password,
                    onValueChange = { password = it },
                    iserror = isError,
                    placeholder = "비밀번호를 입력해주세요.",
                    errortext = "존재하지 않는 계정이거나 비밀번호가 일치하지 않습니다."
                )

                Spacer(modifier = Modifier .height(18.dp))

                Box(modifier = Modifier
                    .fillMaxWidth()
//                    .padding(20.dp, 0.dp)
                ) {

                    Text(text = "비밀번호를 잊으셨나요?",
                        color = FontGray,
                        modifier = Modifier
                            .clickable{
                                navController.navigate("/* 추가해야 함 */")
                            }
                            .align(Alignment.CenterStart)
                    )

                    Text(text = "회원가입",
                        color = FontGray,
                        modifier = Modifier
                            .clickable{
                                navController.navigate("signupIdPw")
                            }
                            .align(Alignment.CenterEnd)
                    )
                }

                Spacer(modifier = Modifier .height(35.dp))

                AuthButton(
                    buttonname = "로그인",
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            signinViewModel.signin(username = username, password = password)
                            if ( signinViewModel.signinSuccess == true ) {
                                navController.navigate("/* 써야 함 */")
                            }
                        } else {
                            isError = true
                        }
                    })
            }
        }

    }
}