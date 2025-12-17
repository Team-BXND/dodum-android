package com.example.dodum_android.network.archive

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.*

interface ArchiveService {
    // 게시글 목록 불러오기
    @GET(DodumUrl.Archive.ALL)
    suspend fun getArchiveList(
        @Query("category") category: String? // category는 nullable 가능 (전체 조회 시 null)
    ): Response<List<ArchiveItem>>

    // 세부 게시글 불러오기
    @GET(DodumUrl.Archive.DETAIL)
    suspend fun getArchiveDetail(
        @Path("id") id: Long
    ): Response<ArchiveDetailData>

    // 게시글 작성
    @POST(DodumUrl.Archive.WRITE)
    suspend fun writeArchive(
        @Body request: ArchiveWriteRequest
    ): Response<ArchiveWriteResponse>

    // 게시글 수정
    @PATCH(DodumUrl.Archive.MODIFY)
    suspend fun modifyArchive(
        @Body request: ArchiveModifyRequest
    ): Response<ArchiveWriteResponse>

    // 게시글 삭제
    @DELETE(DodumUrl.Archive.DELETE)
    suspend fun deleteArchive(
        @Query("archiveId") archiveId: Long
    ): Response<ArchiveDeleteResponse>
}