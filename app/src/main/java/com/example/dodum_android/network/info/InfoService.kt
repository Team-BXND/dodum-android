package com.example.dodum_android.network.info

import com.example.dodum_android.network.DodumUrl
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoService {

    @GET(DodumUrl.Info.GET_INFO)
    suspend fun getInfoList(
        @Query("page") page: Int
    ): InfoListResponse

    /** 정보 작성 */
    @POST(DodumUrl.Info.GET_INFO)
    suspend fun postInfo(
        @Body body: InfoPostRequest
    ): InfoPostResponse

    /** 정보 삭제 */
    @DELETE(DodumUrl.Info.INFO_ID)
    suspend fun deleteInfo(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): InfoDeleteResponse

    /** 정보 수정 */
    @PUT(DodumUrl.Info.INFO_ID)
    suspend fun putInfo(
        @Path("id") id: Int,
        @Body body: InfoPutRequest
    ): InfoPutResponse

    /** 정보 상세 조회 */
    @GET(DodumUrl.Info.INFO_ID)
    suspend fun getInfoDetail(
        @Path("id") id: Int
    ): InfoDetailResponse

    /** 좋아요 */
    @POST(DodumUrl.Info.LIKE)
    suspend fun postLike(
        @Path("id") id: Int
    ): InfoBooleanResponse

    /** 승인 */
    @POST(DodumUrl.Info.APPROVE)
    suspend fun approveInfo(
        @Path("id") id: Int
    ): InfoBooleanResponse

    /** 거절 */
    @POST(DodumUrl.Info.DISAPPROVE)
    suspend fun disapproveInfo(
        @Path("id") id: Int
    ): InfoBooleanResponse
}
