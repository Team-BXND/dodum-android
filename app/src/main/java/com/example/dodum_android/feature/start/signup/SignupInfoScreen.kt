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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

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



        }

    }
}