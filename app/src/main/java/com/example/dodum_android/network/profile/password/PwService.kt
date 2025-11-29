package com.example.dodum_android.network.profile.password

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface PwService {
    @PUT(DodumUrl.Auth.PW)
    suspend fun changePassword(
        @Body request: PwChangeRequest
    ): Response<PwChangeResponse>
}
