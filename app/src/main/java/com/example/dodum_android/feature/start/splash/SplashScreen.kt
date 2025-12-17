package com.example.dodum_android.feature.start.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.theme.MainColor
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("welcome")
//        navController.navigate(NavGroup.ContestList)
        {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainColor)
    ) {
        Icon(painter = painterResource(R.drawable.full_logo),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center))
    }
}