package com.example.dodum_android.network.profile.falsepost

data class FalsePostResponse(
    val status: Int,
    val data: List<FalsePost>
)

data class FalsePost(
    val title: String,
    val date: String,
    val image: String
)