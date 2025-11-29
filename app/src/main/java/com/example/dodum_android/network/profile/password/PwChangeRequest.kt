package com.example.dodum_android.network.profile.password

data class PwChangeRequest(
    val email: String,
    val newPassword: String,
    val passwordCheck: String
)