package com.example.dodum_android.ui.component.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.dodum_android.R
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.button.AnimatedButton

@Composable
fun TopAppBar(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(horizontal = 13.dp)
            .padding(top = 53.dp)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(168.dp)
                .height(55.33.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimatedButton(
            onClick = { navController.navigate(NavGroup.Profile) },
            modifier = Modifier
            .padding(horizontal = 7.66.dp)
            .fillMaxHeight()
        ) {
            Image(
                painterResource(R.drawable.account_circle),
                contentDescription = "profile Icon",
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
