package com.example.dodum_android.ui.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun ArchiveCategory(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            val interactionSource = remember { MutableInteractionSource() }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onCategorySelected(category) }
//                    .width(IntrinsicSize.Min)
                    .wrapContentWidth()
                    .height(IntrinsicSize.Min)
            ) {

                Text(
                    text = category,
                    fontSize = 17.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MainColor else Color(0xFFADADAD),
                    textDecoration = if (isSelected)
                        TextDecoration.Underline
                    else
                        TextDecoration.None
                )

//                Spacer(modifier = Modifier.height(4.dp))
//
//                if (isSelected) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(2.dp)
//                            .background(color = MainColor)
//                    )
//                } else {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(2.dp)
//                            .background(color = Color.Transparent)
//                    )
//                }
            }
        }
    }
}