package com.example.dodum_android.network.profile.mypost

data class MyPostResponse(
    val status: Int,
    val data: List<MyPost>
)

data class MyPost(
    val title: String,
    val date: String,
    val image: String
)