package com.example.dodum_android.ui.component.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun NavBar(
    navController: NavHostController,
    thisScreen: String
) {
    // 순서: info(정보공유) -> archive(아카이브) -> major(전공) -> contest(대회정보) -> misc(기타정보)
    // 구조: (id, label, route)
    val navItems = listOf(
        Triple("info", "정보공유", NavGroup.Share),     // 아이콘: share_icon
        Triple("archive", "아카이브", NavGroup.ArchiveList), // 아이콘: archive_icon
        Triple("major", "전공선택", NavGroup.MajorRecommend), // 아이콘: major_icon
//        Triple("contest", "대회정보", NavGroup.Contest),   // 아이콘: contest_icon
        Triple("misc", "기타정보", NavGroup.MSHARE)       // 아이콘: misc_icon
    )

    // 비활성화 색상 (주석 참고)
    val unselectedColor = Color(0xFFC4C4C4)

    Column(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround, // 아이템 간격을 균등하게
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { (id, label, route) ->
                val isSelected = thisScreen == id

                // 색상 설정
                val contentColor = if (isSelected) MainColor else unselectedColor

                // 아이콘 리소스 설정 (규칙: [id]_icon / [id]_check_icon)
                val iconRes = when (id) {
                    "share" -> if (isSelected) R.drawable.info_check_icon else R.drawable.info_icon
                    "archive" -> if (isSelected) R.drawable.archive_check_icon else R.drawable.archive_icon
                    "major" -> if (isSelected) R.drawable.major_check_icon else R.drawable.major_icon
                    "contest" -> if (isSelected) R.drawable.contest_check_icon else R.drawable.contest_icon
                    "misc" -> if (isSelected) R.drawable.misc_check_icon else R.drawable.misc_icon
                    else -> R.drawable.info_icon // Fallback
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(27.dp)
                        .height(38.dp)
                        .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // 리플 효과 제거
                    ) {
                        if (thisScreen != id) {
                            navController.navigate(route) {
                                // 백스택 관리: 탭 간 이동 시 계속 쌓이지 않도록 설정
                                popUpTo(NavGroup.Share) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                ) {
                    // 아이콘
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = label,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}