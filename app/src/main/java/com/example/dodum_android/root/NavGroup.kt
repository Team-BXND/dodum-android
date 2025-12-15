package com.example.dodum_android.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dodum_android.feature.info.information.InfoScreen
import com.example.dodum_android.feature.info.share.ShareScreen
import com.example.dodum_android.feature.misc.share.MShareScreen
import com.example.dodum_android.feature.major.result.MajorResultScreen
import com.example.dodum_android.feature.major.select.MajorScreen
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

    const val Info = "info"
    const val Share = "share"

    const val MISC = "misc"
    const val MSHARE = "miscShare"

    const val ArchiveList = "archiveList"
    const val ArchiveDetail = "archiveDetail/{archiveId}"
    const val ArchiveWrite = "archiveWrite"
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
        composable(NavGroup.FalsePost) { FalsPostScreen(navController)}
    }
}

fun NavGraphBuilder.infoNavGroup(navController: NavHostController) {
    navigation(startDestination = NavGroup.Share, route = "info_graph") {
        composable(NavGroup.Share) { ShareScreen(navController) }
        composable(NavGroup.Info) { InfoScreen(navController)}
    }
}

fun NavGraphBuilder.miscNavGroup(navController: NavHostController) {
    navigation(startDestination = NavGroup.MISC, route = "misc_graph") {
        composable(NavGroup.MSHARE) { MShareScreen(navController) }
        composable(NavGroup.MISC) { MShareScreen(navController) }
    }
}

fun NavGraphBuilder.majorNavGraph(navController: NavHostController){
    navigation(startDestination = NavGroup.MajorResult, route = "major_graph"){
        composable(NavGroup.MajorRecommend) { MajorScreen(navController) }
        composable(NavGroup.MajorResult) { MajorResultScreen(navController)}
    }
}

fun NavGraphBuilder.archiveNavGraph(navController: NavHostController) {
    navigation(startDestination = NavGroup.ArchiveList, route = "archive_graph")
    {
        // 아카이브 목록 화면
        composable(NavGroup.ArchiveList) {
            ArchiveScreen(navController)
        }

        // 아카이브 상세 화면
        // archiveId 값을 Long 타입 argument로 전달받음
        composable(
            route = NavGroup.ArchiveDetail,
            arguments = listOf(
                navArgument("archiveId") {
                    type = NavType.LongType // 전달되는 archiveId의 타입 지정
                }
            )
        ) { backStackEntry ->
            // 전달받은 archiveId를 꺼냄 (없으면 0L 사용)
            val archiveId = backStackEntry.arguments?.getLong("archiveId") ?: 0L

            // 상세 화면으로 이동하면서 archiveId 전달
            ArchiveDetailScreen(navController, archiveId)
        }

        // 아카이브 작성 화면
        composable(NavGroup.ArchiveWrite) {
            ArchiveWriteScreen(navController)
        }
    }
}