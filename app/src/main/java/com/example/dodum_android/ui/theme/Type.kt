package com.example.dodum_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class DodumTypography(
    val Display: TextStyle,
    val Title: TextStyle,
    val Subtitle: TextStyle,
    val Body: TextStyle,
    val Caption: TextStyle,
    val PlaceHolder: TextStyle,
    val TileTitle: TextStyle,
    val TileBody: TextStyle,
    val PageTitle: TextStyle,
    val TileSubtitle: TextStyle
)

val DodumTypographySet =  DodumTypography(
    Display = TextStyle(
        fontSize = 64.sp,
        fontWeight = FontWeight.SemiBold
    ),
    Title = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.SemiBold
    ),
    Subtitle = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium
    ),
    Body = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    Caption = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    PlaceHolder = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    TileTitle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    TileBody = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    PageTitle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    TileSubtitle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
)
