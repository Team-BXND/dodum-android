package com.example.dodum_android.network.start.email

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {

    @POST(DodumUrl.Email.SEND)
    suspend fun sendEmail(
        @Body request: EmailSendRequest
    ): Response<EmailSendSuccessResponse>

    @POST(DodumUrl.Email.CHECK)
    suspend fun checkEmail(
        @Body request: EmailCheckRequest
    ): Response<EmailSendSuccessResponse>
}