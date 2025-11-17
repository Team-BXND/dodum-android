package com.example.dodum_android.network.profile

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
    val success: Boolean
)

data class MyPostResponse(
    val status: Int,
    val data: List<MyPost>
)

data class MyPost(
    val title: String,
    val date: String,
    val image: String
)

data class ProfileResponse(
    val status: Int,
    val data: Profile
)