package com.example.dodum_android.network.info

// 수정
data class InfoPutRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val image: String
)

// 작성
data class InfoPostRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val image: String
)
