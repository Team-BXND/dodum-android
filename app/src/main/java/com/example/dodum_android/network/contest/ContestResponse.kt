package com.example.dodum_android.network.contest

data class ContestDetailResponse(
    val status: Int,
    val data: ContestData
)

data class ContestListResponse(
    val status: Int,
    val data: List<ContestData>
)

data class ContestData(
    val id: Int = 0, // ★ Long -> Int 변경
    val title: String,
    val content: String,
    val email: String,
    val phone: String,
    val time: String,
    val place: String,
    val image: String,
    val isAlertActive: Boolean = false
)

// 등록, 수정, 삭제용 응답 (success: String)
data class ContestCommonResponse(
    val status: Int,
    val data: SuccessDataString
)

data class SuccessDataString(
    val success: String
)

// 알림 토글용 응답 (success: Boolean)
data class ContestActiveResponse(
    val status: Int,
    val data: ActiveDataBoolean
)

data class ActiveDataBoolean(
    val success: Boolean
)