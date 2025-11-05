package com.example.dodum_android.network.start.signup

data class SignupRequest(
    val username: String,
    val password: String,
    val grade: Int?,
    val class_no: Int?,
    val student_no: Int?,
    val phone: String,
    val email: String,
    val major: String?,
    val history: String?,
    val club: String?
    // "BIND", "3D", "두카미", "Louter", "CNS", "모디", "ALT", "Chatty", "NONE" 중 하나
)