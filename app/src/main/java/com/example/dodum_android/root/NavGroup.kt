package com.example.dodum_android.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dodum_android.feature.info.information.InfoScreen
import com.example.dodum_android.feature.info.share.ShareScreen
import com.example.dodum_android.feature.info.write.IWriteScreen
import com.example.dodum_android.feature.misc.share.MShareScreen
import com.example.dodum_android.feature.major.result.MajorResultScreen
import com.example.dodum_android.feature.major.select.MajorScreen
import com.example.dodum_android.feature.misc.misc.MiscScreen
import com.example.dodum_android.feature.misc.write.MWriteScreen
import com.example.dodum_android.feature.profile.changeinfo.ChangeInformScreen
import com.example.dodum_android.feature.profile.changepw.ChangePwScreen
import com.example.dodum_android.feature.profile.falsepost.FalsPostScreen
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
    const val FalsePost = "falsePost"

    const val Splash = "splash"
    const val Welcome = "welcome"
    const val Signin = "signin"
    const val SignupIdPw = "signupIdPw"
    const val SignupInfo = "signupInfo"
    const val SignupEmail = "signupEmail"

    const val MajorRecommend = "majorRecommend"
    const val MajorResult = "majorResult"

    const val Info = "info/{infoId}"
    const val Share = "share"
    const val IWRITE = "infoWrite"

    const val MISC = "misc/{miscId}"
    const val MSHARE = "miscShare"
    const val MWRITE = "miscWrite"
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
    navigation(startDestination = NavGroup.Profile, route = "profile_graph") {
        composable(NavGroup.Profile) { ProfileScreen(navController) }
        composable(NavGroup.MyInfo) { MyInformScreen(navController) }
        composable(NavGroup.ChangeInfo) { ChangeInformScreen(navController) }
        composable(NavGroup.ChangePw) { ChangePwScreen(navController) }
        composable(NavGroup.MyPosts) { MypostScreen(navController) }
        composable(NavGroup.FalsePost) { FalsPostScreen(navController) }
    }
}

fun NavGraphBuilder.infoNavGroup(navController: NavHostController) {
    navigation(startDestination = NavGroup.Share, route = "info_graph") {
        composable(NavGroup.Share) { ShareScreen(navController) }
        composable(NavGroup.Info) { backStackEntry ->
            val infoId = backStackEntry.arguments
                ?.getString("infoId")
                ?.toInt() ?: return@composable

            InfoScreen(
                navController = navController,
                infoId = infoId
            )
        }
        composable(NavGroup.IWRITE) { IWriteScreen(navController) }
    }
}

fun NavGraphBuilder.miscNavGroup(navController: NavHostController) {
    navigation(startDestination = NavGroup.MSHARE, route = "misc_graph") {
        composable(NavGroup.MSHARE) { MShareScreen(navController) }
        composable(NavGroup.MISC) { backStackEntry ->
            val miscId = backStackEntry.arguments
                ?.getString("miscId")
                ?.toInt() ?: return@composable

            MiscScreen(
                navController = navController,
                miscId = miscId
            )
        }
        composable(NavGroup.MWRITE) { MWriteScreen(navController) }
    }
}

fun NavGraphBuilder.majorNavGraph(navController: NavHostController) {
    navigation(startDestination = NavGroup.MajorRecommend, route = "major_graph") {
        composable(NavGroup.MajorRecommend) { MajorScreen(navController) }
        composable(NavGroup.MajorResult) { MajorResultScreen(navController) }
    }
}
