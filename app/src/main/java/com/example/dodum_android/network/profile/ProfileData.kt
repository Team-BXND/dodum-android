package com.example.dodum_android.network.profile

data class Profile(
    val username: String,
    val studentId: String,
    val grade: String,
    val class_no: String,
    val student_no: String,
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
