package com.example.dodum_android.network.archive

// POST /archive/write 요청
data class ArchiveWriteRequest(
    val title: String,
    val subtitle: String?,
    val content: String,
    val thumbnail: String?, // 필수 포함 (null 가능)
    val category: String,
    val teamname: String?
)

// PATCH /archive 요청 (수정)
data class ArchiveModifyRequest(
    val archiveId: Long,
    val title: String,
    val subtitle: String?,
    val content: String,
    val thumbnail: String?,
    val category: String,
    val teamname: String?
)