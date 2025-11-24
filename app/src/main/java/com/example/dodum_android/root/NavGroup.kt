package com.example.dodum_android.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dodum_android.feature.major.RecommendMajorScreen
import com.example.dodum_android.feature.profile.changeinfo.ChangeInformScreen
import com.example.dodum_android.feature.profile.changepw.ChangePwScreen
import com.example.dodum_android.feature.profile.myinfo.MyInformScreen
import com.example.dodum_android.feature.profile.mypost.MypostScreen
import com.example.dodum_android.feature.profile.profile.ProfileScreen
import com.example.dodum_android.feature.start.signin.SigninScreen
import com.example.dodum_android.feature.start.signup.SignupIdPwScreen
import com.example.dodum_android.feature.start.signup.SignupInfoScreen
import com.example.dodum_android.feature.start.signup.VerifyEmailScreen
import com.example.dodum_android.feature.start.splash.SplashScreen
import com.example.dodum_android.feature.start.welcome.WelcomeSigninScreen

object NavGroup {
    const val MyInfo = "myInfo"
    const val ChangeInfo = "changeInfo"
    const val ChangePw = "changePw"
    const val Profile = "profile"
    const val MyPosts = "myPost"

    const val Splash = "splash"
    const val Welcome = "welcome"
    const val Signin = "signin"
    const val SignupIdPw = "signupIdPw"
    const val SignupInfo = "signupInfo"
    const val SignupEmail = "signupEmail"
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = NavGroup.Splash, route = "auth_graph") {
        composable(NavGroup.Splash) { SplashScreen(navController) }
        composable(NavGroup.Welcome) { WelcomeSigninScreen(navController) }

        composable(NavGroup.Signin) { SigninScreen(navController) }
        composable(NavGroup.SignupIdPw) { SignupIdPwScreen(navController) }
        composable(NavGroup.SignupInfo) { SignupInfoScreen(navController) }
        composable(NavGroup.SignupEmail) { VerifyEmailScreen(navController) }
    }
}

fun NavGraphBuilder.profileNavGroup(navController: NavHostController) {
    navigation(startDestination = NavGroup.Profile, route = "profile_graph"){
        composable(NavGroup.Profile) { ProfileScreen(navController)}
        composable(NavGroup.MyInfo) { MyInformScreen(navController)}
        composable(NavGroup.ChangeInfo) { ChangeInformScreen(navController) }
        composable(NavGroup.ChangePw) { ChangePwScreen(navController) }
        composable(NavGroup.MyPosts) { MypostScreen(navController)}
    }
}

fun NavGraphBuilder.majorNavGraph(navController: NavHostController){
    navigation(startDestination = NavGroup.MajorRecommend, route = "major_graph"){
        composable(NavGroup.MajorRecommend) { RecommendMajorScreen(navController)}
    }
}
