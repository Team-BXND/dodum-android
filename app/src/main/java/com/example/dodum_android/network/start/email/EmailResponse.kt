package com.example.dodum_android.network.start.email

data class EmailSendSuccessResponse(
    val status: Int,
    val data: String
)

data class EmailSendErrorResponse(
    val status: Int,
    val error: ErrorInfo
)

data class ErrorInfo(
    val code: String,
    val message: String,
    val timestamp: String
)