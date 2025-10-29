package com.example.dodum_android.feature.start.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.dodum_android.ui.component.textfield.AuthEmailTextField
import kotlinx.coroutines.launch

@Composable
fun VerifyEmailScreen (
    navController: NavHostController
) {
    val signupViewModel: SignupViewModel = hiltViewModel()

    Box (modifier = Modifier
        .fillMaxSize()
    ){
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

            var email by remember { mutableStateOf("") }
            var authcode by remember { mutableStateOf("") }

            var iserror by remember { mutableStateOf(false) }
            var ischeckCode by remember { mutableIntStateOf(0) }

            val coroutineScope = rememberCoroutineScope()

            AuthEmailTextField(
                value = email,
                onValueChange = { email = it },
                isError = iserror,
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



        }
    }


}