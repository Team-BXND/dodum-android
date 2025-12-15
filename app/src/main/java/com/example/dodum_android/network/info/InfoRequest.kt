package com.example.dodum_android.network.info

// POST /info
data class InfoPostRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val image: String
)

// PUT /info/{id}
data class InfoPutRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val image: String
)

// POST /info/{id}/comments
data class InfoCommentRequest(
    val content: String
)
