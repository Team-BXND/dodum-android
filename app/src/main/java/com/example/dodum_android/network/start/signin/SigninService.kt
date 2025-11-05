package com.example.dodum_android.network.start.signin

import com.example.dodum_android.network.DodumUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninService {
    @POST(DodumUrl.Auth.SIGNIN)
    suspend fun signin(@Body request: SigninRequest): SigninResponse
}