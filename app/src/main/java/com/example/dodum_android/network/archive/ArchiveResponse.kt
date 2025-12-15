package com.example.dodum_android.network.archive

// 목록 조회 응답
data class ArchiveListResponse(
    val status: Int,
    val data: List<ArchiveItem>?
)

data class ArchiveItem(
    val id: Long,
    val title: String,
    val teamname: String,
    val category: String,
    val thumbnail: String? // 이미지 URL
)

// 상세 조회 응답
data class ArchiveDetailResponse(
    val status: Int,
    val data: ArchiveDetailData
)

data class ArchiveDetailData(
    val title: String,
    val subtitle: String,
    val teamname: String,
    val content: String,
    val createdAt: String,
    val thumbnail: String?
)

// 작성/수정 응답
data class ArchiveWriteResponse(
    val status: Int,
    val archiveId: Long
)

// 삭제 응답
data class ArchiveDeleteResponse(
    val status: Int,
    val message: String
)

