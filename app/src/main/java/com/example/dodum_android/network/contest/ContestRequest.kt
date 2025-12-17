package com.example.dodum_android.network.contest

data class ContestCreateRequest(
    val title: String,
    val subtitle: String, // 명세서 포함
    val content: String,
    val phone: String,
    val email: String,
    val time: String, // Date -> String
    val place: String,
    val image: String // 명세서엔 없지만 UI/로직상 필요하여 유지 (필요시 제거)
)

data class ContestUpdateRequest(
    val title: String,
    val content: String,
    val phone: String,
    val email: String,
    val time: String,
    val place: String,
    val image: String
)