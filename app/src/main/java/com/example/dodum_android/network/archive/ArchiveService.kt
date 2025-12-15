package com.example.dodum_android.network.archive

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.*

interface ArchiveService {
    @GET(DodumUrl.Archive.ALL)
    suspend fun getArchiveList(
        @Query("category") category: String
    ): Response<List<ArchiveItem>> // 문서상 Array? -> List로 처리

    @GET(DodumUrl.Archive.DETAIL)
    suspend fun getArchiveDetail(
        @Path("archiveId") archiveId: Long
    ): Response<ArchiveDetailData>

    @POST(DodumUrl.Archive.WRITE)
    suspend fun writeArchive(
        @Body request: ArchiveWriteRequest
    ): Response<ArchiveWriteResponse>

    @PATCH(DodumUrl.Archive.MODIFY)
    suspend fun modifyArchive(
        @Body request: ArchiveModifyRequest
    ): Response<ArchiveWriteResponse>

    @DELETE(DodumUrl.Archive.DELETE)
    suspend fun deleteArchive(
        @Query("archiveId") archiveId: Long
    ): Response<ArchiveDeleteResponse>
}