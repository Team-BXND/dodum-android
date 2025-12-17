package com.example.dodum_android.network.start.signin

data class SigninRequest(
    val username: String,
    val password: String
)

data class RefreshTokenRequest(
    val username: String,
    val refreshToken: String
)