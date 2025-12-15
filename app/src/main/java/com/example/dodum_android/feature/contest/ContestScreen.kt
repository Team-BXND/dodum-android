package com.example.dodum_android.feature.contest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.dodum_android.ui.component.bar.NavBar
import com.example.dodum_android.ui.component.bar.TopAppBar

@Composable
fun ContestScreen(
    navController: NavHostController
) {
//    profileId =
//    val contestViewModel: ContestViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize()
//        .background(Color.Black)
    ) {

        TopAppBar(navController = navController, )


        

        Box (modifier = Modifier
            .align(Alignment.BottomEnd)){
            NavBar(navController = navController, thisScreen = "contest")
        }
    }

}