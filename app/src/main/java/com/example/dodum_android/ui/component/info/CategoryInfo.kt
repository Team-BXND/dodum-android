package com.example.dodum_android.ui.component.info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.network.misc.MiscItemList
import com.example.dodum_android.ui.theme.SubColor

fun mapCategoryToLabel(category: String): String {
    return when (category) {
        "lecture" -> "강의 정보"
        "tool" -> "개발 도구"
        "platform" -> "플랫폼 추천"
        "school_support" -> "학교 지원"
        else -> "기타"
    }
}


@Composable
fun CategoryListItem(
    item: MiscItemList,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
    ) {
        Divider(color = Color(0xFFE3E3E3), thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.padding(start = 20.dp))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .border(
                                width = 1.dp,
                                color = SubColor,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(horizontal = 20.dp)
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = mapCategoryToLabel(item.category),
                            color = SubColor
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = item.author,
                        fontSize = 16.sp,
                        color = Color(0xFFADADAD)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(width = 64.dp, height = 44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray),
            )

//            AsyncImage(
//                model = item.image,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(width = 54.dp, height = 44.dp)
//                    .clip(RoundedCornerShape(12.dp)),
//                contentScale = ContentScale.Crop
//            )
        }

        Divider(color = Color(0xFFE3E3E3), thickness = 1.dp)
    }
}