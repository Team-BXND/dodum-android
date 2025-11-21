package com.example.dodum_android.ui.component.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dodum_android.R

@Composable
fun HomeTopBar(
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 53.dp, start = 13.dp, end = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 로고
        Icon(
            painter = painterResource(id = R.drawable.full_logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .width(144.dp)
//                .height(48.dp)
        )

        // 프로필 아이콘
        IconButton(onClick = { navController.navigate("profile") }) {
            Icon(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = null
//                tint = Color.Unspecified
            )
        }
    }
}