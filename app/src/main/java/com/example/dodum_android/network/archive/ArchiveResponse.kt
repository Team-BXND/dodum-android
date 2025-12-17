package com.example.dodum_android.network.archive

// 목록 조회 응답
data class ArchiveListResponse(
    val status: Int,
    val data: List<ArchiveItem>?
)

data class ArchiveItem(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val teamname: String,
    val content: String, // description 대신 content 사용
    val category: String,
    val createdAt: String, // Instant는 통신 시 String으로 옴
    val thumbnail: String? = null // 목록에 썸네일이 있다면
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
    val createdAt: String, // Instant
    val thumbnail: String? = null, // 상세 조회 시 이미지
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