package com.example.dodum_android.ui.component.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dodum_android.R

data class NavBarItem(
    val iconRes: Int,
    val selectedIconRes: Int,
    val route: String
)

@Composable
fun HomeNavBar(
    navController: NavHostController,
    thisScreen: String
) {
    val navItems = listOf(
        NavBarItem(R.drawable.info_icon, R.drawable.info_check_icon, "info"),
        NavBarItem( R.drawable.archive_icon, R.drawable.archive_check_icon, "archive"),
        NavBarItem(R.drawable.major_icon, R.drawable.major_check_icon, "major"),
        NavBarItem( R.drawable.contest_icon, R.drawable.contest_check_icon, "contest"),
        NavBarItem(R.drawable.misc_icon, R.drawable.misc_check_icon, "misc")
    )

    Column(
        modifier = Modifier
            .height(85.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            navItems.forEach { item ->
                val isSelected = thisScreen.equals(item.route, ignoreCase = true)

                NavItem(
                    iconRes = if (isSelected) item.selectedIconRes else item.iconRes,
                    onClick = {
                        navController.navigate(item.route)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun NavItem(
    iconRes: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .width(32.dp)
                .height(42.dp)
        )
    }
}