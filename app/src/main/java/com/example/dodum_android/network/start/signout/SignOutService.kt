package com.example.dodum_android.network.start.signout

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface SignOutService {
    @POST(DodumUrl.Auth.SIGNOUT)
    suspend fun signOut(
        @Body request: SignOutRequest
    ): Response<SignOutResponse>
}