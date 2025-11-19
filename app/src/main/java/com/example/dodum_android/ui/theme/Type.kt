package com.example.dodum_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)

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

val DodumTypographySet = DodumTypography(
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
