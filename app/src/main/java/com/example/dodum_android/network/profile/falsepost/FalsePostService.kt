package com.example.dodum_android.network.profile.falsepost

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.GET

interface FalsePostService {
    @GET(DodumUrl.Profile.FALSEPOST)
    suspend fun getFalsePosts(): Response<FalsePostResponse>
}