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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.button.AuthButton
import com.example.dodum_android.ui.component.textfield.AuthEmailTextField
import com.example.dodum_android.ui.component.textfield.AuthTextField
import kotlinx.coroutines.launch

@Composable
fun VerifyEmailScreen (
    navController: NavHostController
) {
    val signupViewModel: SignupViewModel = hiltViewModel()

    Box (modifier = Modifier
        .fillMaxSize()
    ){
        Column ( modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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

            var email by remember { mutableStateOf("") }
            var authcode by remember { mutableStateOf("") }

            var isEmailerror by remember { mutableStateOf(false) }
            var isAuthCodeerror by remember {mutableStateOf(false) }

            var iserror by remember { mutableStateOf(false) }
            var ischeckCode by remember { mutableIntStateOf(0) }

            val coroutineScope = rememberCoroutineScope()

            AuthEmailTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                value = email,
                onValueChange = { email = it },
                isError = isEmailerror,
                ischeck = ischeckCode,
                onclick = {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        coroutineScope.launch {
//                            val result = signupViewModel.sendEmail(email)
//                            if (result) {
//                                ischeckCode += 1
//                            }
                        }
                    } else {
                        iserror = true
                    }
                },
                errortext = "이메일 형식이 올바르지 않습니다."
            )

            Spacer(modifier = Modifier .height(12.dp))

            AuthTextField(
                fieldname = "인증번호",
                placeholder = "인증번호를 입력해주세요.",
                value = authcode,
                onValueChange = { authcode = it },
                iserror = isAuthCodeerror,
            )

            Spacer(modifier = Modifier .height(35.dp))


            AuthButton("회원가입", onClick = {
                when {
                    email.isBlank() -> isEmailerror = true
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> isEmailerror = true
                    authcode.isBlank() -> isAuthCodeerror = true
                    !authcode.matches(Regex("^\\d{6}\$")) -> isAuthCodeerror = true
                    else -> {
                        coroutineScope.launch {
//                            val emailChecked = signupViewModel.checkEmail(email, authcode) // suspend
//                            if (emailChecked) {
//                                val signupSuccess = signupViewModel.signup(email, selectedRole) // suspend
//                                if (signupSuccess) navController.navigate("login")
//                            } else {
//                                isAuthCodeerror = true
//                            }
                        }
                    }
                }
            })


        }
    }


}