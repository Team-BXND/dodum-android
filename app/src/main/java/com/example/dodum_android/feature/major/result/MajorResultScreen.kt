package com.example.dodum_android.feature.major.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.feature.major.select.MajorViewModel
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.theme.MainColor
import com.example.dodum_android.ui.theme.SubColor
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.util.CoilUtils
import com.example.dodum_android.network.major.Graph
import com.example.dodum_android.ui.component.graph.MajorGraphChart

@Composable
fun MajorResultScreen(
    navController: NavController
){
    val viewModel: MajorViewModel = hiltViewModel()
    val majorResponse = viewModel.result.collectAsState().value

    val majorkey = majorResponse?.majorkey
    val major = majorResponse?.major
//    val major = "웹 개발자"
//    val majorkey = "web"
    val majorInfo = majors[majorkey?.lowercase() ?: ""]
//    val testGraph = Graph(
//        web = 80,
//        server = 60,
//        iOS = 70,
//        android = 90
//    )



    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController)

        Spacer(modifier = Modifier.height(16.dp))

        // 추천 전공 이름
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SubColor, fontWeight = FontWeight.Bold)) {
                    append("${major ?: ""}")
                }
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("가 적성에 맞네요!")
                }
            },
            fontSize = 29.sp
        )

        majorInfo?.imageRes?.let { res ->
            Image(
                painter = painterResource(id = res),
                contentDescription = major,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 123.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 전공 설명
        Box(
            modifier = Modifier
                .padding(horizontal = 37.dp)
                .shadow(8.dp, spotColor = MainColor , shape =  RoundedCornerShape(16.dp), clip = false)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MainColor, fontWeight = FontWeight.Bold, fontSize = 29.sp)) {
                            append("${major ?: ""}")
                        }
                        withStyle(style = SpanStyle(color = MainColor, fontWeight = FontWeight.Bold, fontSize = 29.sp)) {
                            append("란?")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = majorInfo?.description ?: "",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "주요 기술: ${majorInfo?.skills?.joinToString(", ") ?: ""}",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 선정 이유
        Box(
            modifier = Modifier
                .padding(horizontal = 37.dp)
                .shadow(8.dp, spotColor = MainColor , shape =  RoundedCornerShape(16.dp), clip = false)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "선정 이유",
                    color = MainColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 29.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = majorResponse?.SelectedReason ?: "",
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 37.dp)
                .shadow(8.dp, spotColor = MainColor , shape =  RoundedCornerShape(16.dp), clip = false)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "전공별 적성",
                    color = MainColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 29.sp
                )

                Spacer(modifier = Modifier.height(5.dp))
            majorResponse?.graph?.let { graph ->
                MajorGraphChart(graph)
            }
//                MajorGraphChart(testGraph)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
