package com.example.dodum_android.network.info

// 성공여부
data class InfoBooleanResponse(
    val success: Boolean
)

// 목록 조회
data class InfoListResponse(
    val status: Int,
    val data: List<InfoItem>
)

data class InfoItem(
    val id: Int,
    val title: String,
    val author: String,
    val likes: Int,
    val view: Int,
    val comment: Int, // 서버에 있으니 유지
    val image: String
)

// 상세 조회
data class InfoDetailResponse(
    val status: Int,
    val data: InfoDetail
)

data class InfoDetail(
    val title: String,
    val content: String,
    val author: String,
    val date: String
)

// 작성
data class InfoPostResponse(
    val status: Int,
    val data: String
)

// 수정
data class InfoPutResponse(
    val status: Int,
    val data: String
)

// 삭제
data class InfoDeleteResponse(
    val status: Int,
    val data: String
)
