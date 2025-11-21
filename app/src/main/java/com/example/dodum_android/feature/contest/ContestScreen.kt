package com.example.dodum_android.feature.contest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.navbar.HomeNavBar
import com.example.dodum_android.ui.component.navbar.HomeTopBar

@Composable
fun ContestScreen(
    navController: NavHostController
) {
//    val contestViewModel: ContestViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize()
//        .background(Color.Black)
    ) {

        HomeTopBar(navController = navController)


        

        Box (modifier = Modifier
            .align(Alignment.BottomEnd)){
            HomeNavBar(navController = navController, thisScreen = "contest")
        }
    }

}