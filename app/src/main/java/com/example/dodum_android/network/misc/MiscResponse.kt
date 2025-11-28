package com.example.dodum_android.network.misc

data class CommonSuccessResponse(
    val success: Boolean
)

data class MiscDetailResponse(
    val success: Boolean,
    val data: MiscDetailDto?
)

data class MiscDetailDto(
    val title: String,
    val content: String,
    val likes: Int,
    val images: List<String>,
    val createdAt: String
)

data class MiscListResponse(
    val success: Boolean,
    val data: MiscListData?
)

data class MiscListData(
    val infos: List<MiscItemDto>
)

data class MiscItemDto(
    val id: Int,
    val title: String,
    val content: String,
    val likes: Int,
    val images: List<String>,
    val createdAt: String
)
