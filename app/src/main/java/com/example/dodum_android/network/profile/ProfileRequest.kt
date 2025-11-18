package com.example.dodum_android.network.profile

data class UpdateProfileRequest(
    val username: String,
    val grade: Int,
    val class_no: Int,
    val student_no: Int,
    val phone: String,
    val email: String,
    val club: String
)
