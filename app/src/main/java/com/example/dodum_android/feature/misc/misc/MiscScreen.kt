package com.example.dodum_android.feature.misc.misc

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.SubColor

@Composable
fun MiscScreen(
    navController: NavController,
    miscId: Int
) {
    val viewModel: MiscViewModel = hiltViewModel()
    val detail by viewModel.detail.collectAsState()

    LaunchedEffect(miscId) {
        viewModel.loadDetail(miscId)
    }

    if (detail == null) return

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            TopBar(navController)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {

                /** category */
                Text(
                    text = detail!!.category,
                    fontSize = 14.sp,
                    color = SubColor
                )

                /** title */
                Text(
                    text = detail!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B1B1B)
                )

                Spacer(modifier = Modifier.height(20.dp))

                /** author + date */
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.Gray, CircleShape)
                    )

                    Column {
                        Text(
                            text = detail!!.author,
                            fontSize = 12.sp,
                            color = Color(0xFF1B1B1B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Divider(color = Color(0xFFE3E3E3))

                Spacer(modifier = Modifier.height(16.dp))

                /** image 영역 (유지) */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.LightGray, RoundedCornerShape(14.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                /** content */
                Text(
                    text = detail!!.content,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF1B1B1B)
                )
            }
        }
    }
}