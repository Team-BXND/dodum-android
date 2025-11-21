package com.example.dodum_android.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dodum_android.feature.start.signin.SigninScreen
import com.example.dodum_android.feature.start.signup.SignupIdPwScreen
import com.example.dodum_android.feature.start.signup.SignupInfoScreen
import com.example.dodum_android.feature.start.signup.VerifyEmailScreen
import com.example.dodum_android.feature.start.splash.SplashScreen
import com.example.dodum_android.feature.start.welcome.WelcomeSigninScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = "splash", route = "auth_graph") {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeSigninScreen(navController) }

        composable("signin") { SigninScreen(navController) }
        composable("signupIdPw") { SignupIdPwScreen(navController) }
        composable("signupInfo") { SignupInfoScreen(navController) }
        composable("signupEmail") { VerifyEmailScreen(navController) }
    }
}