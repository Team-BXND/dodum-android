package com.example.dodum_android.network.misc

import com.example.dodum_android.network.DodumUrl
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MiscService {

    @GET(DodumUrl.Misc.BASE)
    suspend fun getMiscList(
        @Query("category") category: String,
        @Query("criteria") criteria: String,
        @Query("page") page: Int
    ): MiscListResponse

    @GET(DodumUrl.Misc.MISC_ID)
    suspend fun getMiscDetail(
        @Path("id") id: Int
    ): MiscDetailResponse

    @POST(DodumUrl.Misc.BASE)
    suspend fun createMisc(
        @Body request: MiscCreateRequest
    ): MiscDetailResponse

    @POST(DodumUrl.Misc.MISC_LIKE)
    suspend fun likeMisc(
        @Path("id") id: Int
    ): CommonSuccessResponse

    @POST(DodumUrl.Misc.MISC_APPROVE)
    suspend fun approveMisc(
        @Path("id") id: Int
    ): CommonSuccessResponse

    @POST(DodumUrl.Misc.MISC_DISAPPROVE)
    suspend fun disapproveMisc(
        @Path("id") id: Int
    ): CommonSuccessResponse
}