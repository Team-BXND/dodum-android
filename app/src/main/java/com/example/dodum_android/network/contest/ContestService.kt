package com.example.dodum_android.network.contest

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.*

interface ContestService {
    @GET(DodumUrl.Contest.ID)
    suspend fun getContestDetail(
        @Path("id") id: Int
    ): Response<ContestDetailResponse>

    @GET(DodumUrl.Contest.BASE)
    suspend fun getContestList(
        @Query("page") page: Int
    ): Response<ContestListResponse>

    @POST(DodumUrl.Contest.BASE)
    suspend fun createContest(
        @Body request: ContestCreateRequest
    ): Response<ContestCommonResponse>

    @PUT(DodumUrl.Contest.ID)
    suspend fun updateContest(
        @Path("id") id: Int,
        @Body request: ContestUpdateRequest
    ): Response<ContestCommonResponse>

    @DELETE(DodumUrl.Contest.ID)
    suspend fun deleteContest(
        @Path("id") id: Long
    ): Response<ContestCommonResponse>

    @POST(DodumUrl.Contest.ACTIVE)
    suspend fun toggleContestAlert(
        @Path("id") id: Long
    ): Response<ContestActiveResponse>
}