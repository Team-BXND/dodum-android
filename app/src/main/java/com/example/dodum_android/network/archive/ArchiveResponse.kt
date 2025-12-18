package com.example.dodum_android.network.archive

// GET /archive/all 응답
data class ArchiveListResponse(
    val status: Int,
    val data: List<ArchiveItem>?
)

// List<{...}> 형태 매핑
data class ArchiveItem(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val teamname: String?,
    val content: String,
    val category: String,
    val createdAt: String, // Instant 타입은 JSON에서 String으로 옴 (예: "2023-12-17T10:00:00Z")
    val thumbnail: String?
)

// GET /archive/{id} 상세 조회 응답
data class ArchiveDetailResponse(
    val status: Int,
    val data: ArchiveDetailData
)

data class ArchiveDetailData(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val teamname: String?,
    val content: String,   // contents -> content 확인
    val category: String,
    val createdAt: String, // Instant
    val thumbnail: String?
)

// 작성/수정 응답
data class ArchiveWriteResponse(
    val status: String,
    val archiveId: Long
)

// 삭제 응답
data class ArchiveDeleteResponse(
    val status: String, // 명세서에 status: String으로 변경됨 확인
    val message: String
)