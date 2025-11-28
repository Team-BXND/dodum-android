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


    @GET(DodumUrl.Info.INFO_CATEGORY)
    suspend fun getInfoByCategory(
        @Path("category") category: String,
        @Query("search") search: String,
        @Query("page") page: Int
    ): InfoCategoryResponse


    @POST(DodumUrl.Info.GET_INFO)
    suspend fun postInfo(
        @Body body: InfoPostRequest
    ): InfoPostResponse


    @DELETE(DodumUrl.Info.INFO_ID)
    suspend fun deleteInfo(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): InfoDeleteResponse


    @PUT(DodumUrl.Info.INFO_ID)
    suspend fun putInfo(
        @Path("id") id: Int,
        @Body body: InfoPutRequest
    ): InfoPutResponse


    @GET(DodumUrl.Info.INFO_ID)
    suspend fun getInfoDetail(
        @Path("id") id: Int
    ): InfoDetailResponse

    @POST(DodumUrl.Info.LIKE)
    suspend fun postLike(
        @Path("id") id: Int
    ): InfoLikeResponse


    @POST(DodumUrl.Info.APPROVE)
    suspend fun approveInfo(
        @Path("id") id: Int
    ): InfoApproveResponse


    @POST(DodumUrl.Info.DISAPPROVE)
    suspend fun disapproveInfo(
        @Path("id") id: Int
    ): InfoDisapproveResponse
}
