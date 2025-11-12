package com.example.dodum_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    onClick: ()-> Unit,
    background: Color,
    height: Dp,
){

    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(background, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 19.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}