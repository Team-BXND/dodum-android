package com.example.dodum_android.root

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dodum_android.feature.profile.ChangeInformScreen
import com.example.dodum_android.feature.profile.ChangePwScreen
import com.example.dodum_android.feature.profile.MyInformScreen
import com.example.dodum_android.feature.profile.MypostsScreen
import com.example.dodum_android.feature.profile.ProfileScreen
import com.example.dodum_android.feature.profile.ProfileViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController, startDestination = "${NavGroup.Profile}/3" // 시작 스크린
    ) {
        composable("${NavGroup.MyInform}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()
            MyInformScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${NavGroup.ChangeInform}/{id}") { backStackEnrty ->
            val id = backStackEnrty.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()

            ChangeInformScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${NavGroup.ChangePw}/{id}") { backStackEnrty ->
            val id = backStackEnrty.arguments?.getString("id")?.toIntOrNull() ?: 0
            ChangePwScreen(
                navController = navController,
                profileId = id
            )
        }

        composable("${NavGroup.Profile}/{id}"){backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${NavGroup.MyPosts}/{id}"){backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()
            MypostsScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

    }

}