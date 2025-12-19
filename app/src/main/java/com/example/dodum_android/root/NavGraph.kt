package com.example.dodum_android.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "auth_graph") { // ← NavGraph
        authNavGraph(navController)   // NavGroup 호출
//        mainNavGraph(navController)   // NavGroup 호출
        profileNavGroup(navController)
    }
}