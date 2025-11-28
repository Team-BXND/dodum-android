package com.example.dodum_android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "info_graph") { // ← NavGraph
        infoNavGroup(navController)
        profileNavGroup(navController)
        authNavGraph(navController)
//        mainNavGraph(navController)   // NavGroup 호출
    }
}