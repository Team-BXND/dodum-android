package com.example.dodum_android.network.major

data class MajorRecommendResponse(
    val name: String,
    val description: String,
    val reason: List<String>,
    val aptitudes: List<Float>,
)