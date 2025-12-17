package com.example.dodum_android.network.contest

data class ContestCreateRequest(
    val title: String,
    val subtitle: String, // API 명세에는 있으나 피그마 UI에는 안보임, 일단 포함
    val content: String,
    val phone: String,
    val email: String,
    val time: String,
    val place: String,
    val image: String
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