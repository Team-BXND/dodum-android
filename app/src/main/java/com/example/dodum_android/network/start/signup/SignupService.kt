package com.example.dodum_android.network.start.signup

import com.example.dodum_android.network.DodumUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST(DodumUrl.Auth.SIGNUP)
    suspend fun signup(@Body request: SignupRequest): SignupResponse
}