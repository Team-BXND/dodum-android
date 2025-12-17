package com.example.dodum_android.network.archive

// 작성 요청
data class ArchiveWriteRequest(
    val userId: String,
    val title: String,
    val subtitle: String,
    val content: String,
    val thumbnail: String,
    val category: String,
    val teamname: String
)

// 수정 요청
data class ArchiveModifyRequest(
    val archiveId: Long,
    val title: String,
    val subtitle: String,
    val content: String,
    val category: String?
)
