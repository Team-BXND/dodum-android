package com.example.dodum_android.network.profile.myinfo

data class UpdateProfileRequest(
    val grade: Int,
    val class_no: Int,
    val student_no: Int,
    val phone: String,
    val email: String,
    val club: String
)