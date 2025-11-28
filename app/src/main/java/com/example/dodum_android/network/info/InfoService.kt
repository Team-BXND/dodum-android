package com.example.dodum_android.network.info

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InfoService {

    @GET("/info")
    suspend fun getInfoList(
        @Query("page") page: Int
    ): InfoListResponse

    @GET("/info/{category}")
    suspend fun getInfoByCategory(
        @Path("category") category: String,
        @Query("search") search: String,
        @Query("page") page: Int
    ): InfoCategoryResponse

    @POST("/info")
    suspend fun postInfo(
        @Body body: InfoPostRequest
    ): InfoPostResponse

    @DELETE("/info/{id}")
    suspend fun deleteInfo(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): InfoDeleteResponse

    @PUT("/info/{id}")
    suspend fun putInfo(
        @Path("id") id: Int,
        @Body body: InfoPutRequest
    ): InfoPutResponse

    @GET("/info/{id}")
    suspend fun getInfoDetail(
        @Path("id") id: Int
    ): InfoDetailResponse

    @POST("/info/{id}/comments")
    suspend fun postComment(
        @Path("id") id: Int,
        @Body body: InfoCommentRequest
    ): InfoCommentResponse

    @POST("/info/{id}/like")
    suspend fun postLike(
        @Path("id") id: Int
    ): InfoLikeResponse

    @POST("/info/{id}/approve")
    suspend fun approveInfo(
        @Path("id") id: Int
    ): InfoApproveResponse

    @POST("/info/{id}/disapprove")
    suspend fun disapproveInfo(
        @Path("id") id: Int
    ): InfoDisapproveResponse
}
