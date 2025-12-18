package com.example.dodum_android.network.archive

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.*

interface ArchiveService {
    // [수정] 응답 타입을 ArchiveDetailData -> ArchiveDetailResponse로 변경 (명세서 반영)
    @GET(DodumUrl.Archive.DETAIL)
    suspend fun getArchiveDetail(
        @Path("id") id: Long
    ): Response<ArchiveDetailData>

    @POST(DodumUrl.Archive.WRITE)
    suspend fun writeArchive(
        @Body request: ArchiveWriteRequest
    ): Response<ArchiveWriteResponse>

    @PATCH(DodumUrl.Archive.MODIFY)
    suspend fun modifyArchive(
        @Body request: ArchiveModifyRequest
    ): Response<ArchiveWriteResponse>

    // [수정] path를 "archive"로 하드코딩하여 슬래시 중복 방지
    // hasBody = true를 넣어 DELETE 요청에 Body를 실어 보냄
    @HTTP(method = "DELETE", path = "archive", hasBody = true)
    suspend fun deleteArchive(
        @Body request: Map<String, Long> // Body: {"archiveId": 13}
    ): Response<ArchiveDeleteResponse>

    @GET(DodumUrl.Archive.ALL)
    suspend fun getArchiveList(
        @Query("category") category: String?
    ): Response<List<ArchiveItem>>
}