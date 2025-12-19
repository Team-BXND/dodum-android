package com.example.dodum_android.ui.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val graphRoute: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem("info_graph", "정보공유", Icons.Outlined.Share),
        BottomNavItem("misc_graph", "기타정보", Icons.Outlined.Bookmark),
        BottomNavItem("major_graph", "전공선택", Icons.Outlined.Search)
    )

    var selectedGraphRoute by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFFAFAFA))
            .border(1.dp, Color.Black.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = selectedGraphRoute == item.graphRoute

                Column(
                    modifier = Modifier.clickable {
                        selectedGraphRoute = item.graphRoute   // ⭐ 여기서 선택
                        navController.navigate(item.graphRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (selected) Color(0xFF26874E) else Color.Transparent,
                                shape = RoundedCornerShape(9.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) Color.White else Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.label,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = (-0.05).em,
                        color = if (selected) Color(0xFF26874E) else Color.Black
                    )
                }
            }
        }
    }
}