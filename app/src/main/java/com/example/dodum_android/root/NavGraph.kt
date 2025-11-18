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
        navController = navController, startDestination = "${Screens.Profile.route}/3" // 시작 스크린
    ) {
        composable("${Screens.MyInform.route}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()
            MyInformScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${Screens.ChangeInform.route}/{id}") { backStackEnrty ->
            val id = backStackEnrty.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()

            ChangeInformScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${Screens.ChangePw.route}/{id}") { backStackEnrty ->
            val id = backStackEnrty.arguments?.getString("id")?.toIntOrNull() ?: 0
            ChangePwScreen(
                navController = navController,
                profileId = id
            )
        }

        composable("${Screens.Profile.route}/{id}"){backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = id
            )
        }

        composable("${Screens.MyPosts.route}/{id}"){backStackEntry ->
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