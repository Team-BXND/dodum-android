package com.example.dodum_android.network.contest

data class ContestDetailResponse(
    val status: Int,
    val data: ContestData
)

data class ContestListResponse(
    val status: Int,
    val data: List<ContestData> // 문서상 data: { ... } 이지만 목록이므로 List로 가정
)

data class ContestData(
    val id: Long = 0, // id 추가 (목록/상세 공통 사용 위함)
    val title: String,
    val subTitle: String,
    val email: String,
    val phone: String,
    val time: String, // Date -> String (yyyy-MM-dd 등 포맷팅된 문자열로 처리 가정)
    val place: String,
    val image: String,
    val isAlertActive: Boolean = false // 알림 상태 (API에는 명시되지 않았으나 UI 상태 관리용)
)

data class ContestCommonResponse(
    val status: Int,
    val data: SuccessData
)

data class SuccessData(
    val success: String // or Boolean based on API spec
)

data class ContestActiveResponse(
    val status: Int,
    val data: ActiveData
)

data class ActiveData(
    val success: Boolean
)