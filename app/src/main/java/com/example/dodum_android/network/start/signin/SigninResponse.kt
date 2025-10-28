package com.example.dodum_android.network.start.signin

data class SigninResponse(
    val status: Int,
    val data: SigninData? = null,
    val error: SigninError? = null
)

data class SigninData(
    val accessToken: String,
    val refreshToken: String
)

data class SigninError(
    val code: String,
    val message: String,
    val timestamp: String
)
