package com.example.dodum_android.network.major

data class MajorRecommendResponse(
    val major : String,
    val majorkey : String,
    val SelectedReason : String,
    val graph : Graph
)


data class Graph(
    val web: Int,
    val server: Int,
    val game: Int,
    val iOS: Int,
    val android: Int,
    val ai: Int
)