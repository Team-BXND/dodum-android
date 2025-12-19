package com.example.dodum_android.network.profile.password

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface PwService {
    @POST("/auth/pwchage")
    suspend fun changePassword(
        @Body request: PwChangeRequest
    ): Response<PwChangeResponse>
}
