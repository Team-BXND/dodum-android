package com.example.dodum_android.network.profile.myinfo

data class ProfileResponse(
    val status: Int,
    val data: Profile
)

data class Profile(
    val username: String,
    val grade: Int,
    val class_no: Int,
    val student_no: Int,
    val phone: String,
    val email: String,
    val club: String
)


data class UpdateProfileResponse(
    val status: Int,
    val data: String
)