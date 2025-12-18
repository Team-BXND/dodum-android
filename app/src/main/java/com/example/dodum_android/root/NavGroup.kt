package com.example.dodum_android.root

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.dodum_android.feature.archive.ArchiveScreen
import com.example.dodum_android.feature.archive.write.ArchiveWriteScreen
import com.example.dodum_android.feature.archive.detail.ArchiveDetailScreen
import com.example.dodum_android.feature.contest.ContestScreen
import com.example.dodum_android.feature.contest.write.ContestWriteScreen
import com.example.dodum_android.feature.contest.detail.ContestDetailScreen
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
import com.example.dodum_android.feature.start.signup.SignupViewModel
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
    const val ArchiveModify = "archiveModify/{archiveId}"

    const val ContestList = "contestList"
    const val ContestWrite = "contestWrite"
    const val ContestDetail = "contestDetail/{contestId}"
    const val ContestModify = "contestModify/{contestId}"
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = NavGroup.Splash, route = "auth_graph") {
        composable(NavGroup.Splash) { SplashScreen(navController) }
        composable(NavGroup.Welcome) { WelcomeSigninScreen(navController) }
        composable(NavGroup.Signin) { SigninScreen(navController) }

        // [핵심] 회원가입 프로세스에서 ViewModel 공유
        // SignupIdPwScreen
        composable(NavGroup.SignupIdPw) { backStackEntry ->
            // "auth_graph"라는 라우트를 가진 부모 엔트리를 찾습니다.
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("auth_graph")
            }
            // 부모 엔트리의 스코프로 ViewModel을 가져옵니다. (없으면 생성, 있으면 재사용)
            val viewModel: SignupViewModel = hiltViewModel(parentEntry)

            // ViewModel을 화면에 주입합니다.
            SignupIdPwScreen(navController, viewModel)
        }

        // SignupInfoScreen
        composable(NavGroup.SignupInfo) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("auth_graph")
            }
            // 위와 동일한 "auth_graph" 스코프이므로, 같은 ViewModel 인스턴스를 반환받습니다.
            val viewModel: SignupViewModel = hiltViewModel(parentEntry)

            SignupInfoScreen(navController, viewModel)
        }

        // VerifyEmailScreen
        composable(NavGroup.SignupEmail) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("auth_graph")
            }
            val viewModel: SignupViewModel = hiltViewModel(parentEntry)

            VerifyEmailScreen(navController, viewModel)
        }
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
    navigation(startDestination = NavGroup.ArchiveList, route = "archive_graph") {
        composable(NavGroup.ArchiveList) { ArchiveScreen(navController) }
        composable(
            route = NavGroup.ArchiveDetail,
            arguments = listOf(navArgument("archiveId") { type = NavType.LongType })
        ) { backStackEntry ->
            val archiveId = backStackEntry.arguments?.getLong("archiveId") ?: 0L
            ArchiveDetailScreen(navController, archiveId)
        }
        composable(NavGroup.ArchiveWrite) {
            ArchiveWriteScreen(navController)
        }
        composable(
            route = NavGroup.ArchiveModify,
            arguments = listOf(navArgument("archiveId") { type = NavType.LongType })
        ) { backStackEntry ->
            val archiveId = backStackEntry.arguments?.getLong("archiveId")
            ArchiveWriteScreen(navController, archiveId = archiveId)
        }
    }
}

fun NavGraphBuilder.contestNavGraph(navController: NavHostController) {
    navigation(startDestination = NavGroup.ContestList, route = "contest_graph") {
        composable(NavGroup.ContestList) { ContestScreen(navController) }

        // 작성 (새 글)
        composable(NavGroup.ContestWrite) {
            ContestWriteScreen(navController) // contestId = null
        }

        // 수정 (기존 글)
        composable(
            route = NavGroup.ContestModify,
            arguments = listOf(navArgument("contestId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("contestId")
            ContestWriteScreen(navController, contestId = id) // ID 전달
        }

        composable(
            route = NavGroup.ContestDetail,
            arguments = listOf(navArgument("contestId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("contestId") ?: 0
            ContestDetailScreen(navController, id)
        }
    }
}
