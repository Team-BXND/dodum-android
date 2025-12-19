package com.example.dodum_android.network.major

data class MajorRecommendResponse(
    val major : String,
    val majorKey: String,
    val selectedReason: String,
    val graph : Graph
)


data class Graph(
    val web: Int,
    val server: Int,
    val ios: Int,
    val android: Int,
)