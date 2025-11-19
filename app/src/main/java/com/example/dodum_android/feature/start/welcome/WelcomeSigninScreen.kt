package com.example.dodum_android.feature.start.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dodum_android.R
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun WelcomeSigninScreen (navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize() ) {

        Image(painter = painterResource(R.drawable.firstbackground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize() )

        Button(onClick = {
            navController.navigate("signin")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(260.dp)
                .height(45.dp)
                .align(Alignment.Center)

        ) { Text(text = "로그인하여 돋움을 시작하기",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
            )
        }

    }
}